package com.example.nebeng.feature_a_homepage.domain.interactor.customer.nebeng_motor

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingSession
import com.example.nebeng.feature_a_homepage.domain.usecase.NebengMotorUseCases
import javax.inject.Inject
import com.example.nebeng.core.common.Result
import com.example.nebeng.core.utils.BookingStatus
import com.example.nebeng.core.utils.PaymentStatus
import com.example.nebeng.core.utils.VehicleType
import com.example.nebeng.feature_a_homepage.domain.mapper.*
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerRideCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PaymentMethodCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.TerminalCustomer
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingStep
import com.example.nebeng.feature_passenger_ride_booking.data.remote.model.request.CreatePassengerRideBookingRequest
import com.example.nebeng.feature_passenger_transaction.data.remote.model.request.CreatePassengerTransactionRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.last
import java.time.Instant
import java.time.OffsetDateTime

class NebengMotorBookingInteractor @Inject constructor(
    val useCases: NebengMotorUseCases
) {
    /**
     * Mengambil semua terminal (list tempat keberangkatan & kedatangan).
     */
    suspend fun loadTerminals(token: String): Result<List<TerminalCustomer>> {
        return try {
            val res = useCases.getAllTerminal(token).last()
            if (res is Result.Success) {
                Result.Success(res.data.map { it.toTerminalCustomer() })
            } else {
                Result.Error("Gagal memuat terminal")
            }
        } catch (e: Exception) {
            Result.Error(e.message, e)
        }
    }

    /**
     * LOAD INITIAL (Step pertama)
     * Mengambil semua data statis yang dibutuhkan sebelum user memilih ride.
     */
    suspend fun loadInitial(
        token: String,
        customerId: Int
    ): Result<BookingSession> {
        return try {
            // Initial set vehicleType as a MOTOR
            val setVehicleMotor = VehicleType.MOTOR

            val customerResult      = useCases.getByIdCustomer(token, customerId).last()
            val ridesResult         = useCases.getAllPassengerRide(token).last()
            val terminalsResult     = useCases.getAllTerminal(token).last()
            val paymentMethodResult = useCases.getAllPaymentMethod(token).last()
            val pricingResult       = useCases.getALlPassengerPricing(token).last()

            // --- UNWRAP SATU2, JANGAN DI-"SEMUA HARUS SUKSES" ---
            val customer = when (customerResult) {
                is Result.Success -> customerResult.data.toCustomerCurrentCustomer()
                is Result.Error   -> null   // atau langsung return Error kalau kamu mau hard-fail
                else              -> null
            }

            val rides = when (ridesResult) {
                is Result.Success -> ridesResult.data.map { it.toPassengerRideCustomer() }
                else              -> emptyList()
            }

            val terminals = when (terminalsResult) {
                is Result.Success -> terminalsResult.data.map { it.toTerminalCustomer() }
                else              -> emptyList()
            }

            val paymentMethods = when (paymentMethodResult) {
                is Result.Success -> paymentMethodResult.data.map { it.toPaymentMethoCustomer() }
                else              -> emptyList()
            }

            val pricings = when (pricingResult) {
                is Result.Success -> pricingResult.data.map { it.toPassengerPricingCustomer() }
                else              -> emptyList()
            }

            // Kalau kamu mau: kalau customer null → gagal total
            if (customer == null) {
                return Result.Error("Gagal memuat data customer")
            }

            Result.Success(
                BookingSession(
                    customer                = customer,
                    listPassengerRides      = rides,
                    listTerminals           = terminals,           // ← terisi kalau endpoint terminal sukses
                    listPaymentMethods      = paymentMethods,
                    listPassengerPricing    = pricings,
                    vehicleType             = setVehicleMotor       // wajib
                )
            )

        } catch (e: Exception) {
            Result.Error("LoadInitial error: ${e.message}", e)
        }
    }

    /**
     * SHOW FILTERED PASSENGER RIDE SCHEDULE
     * Filtered by departureTerminal, arrivalTerminal, and departureTime
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun filterRides(
        session: BookingSession
    ): BookingSession {
        val departure = session.selectedDepartureTerminal?.id
        val arrival = session.selectedArrivalTerminal?.id
        val date = session.selectedDate

        if (departure == null || arrival == null || date == null) {
            return session.copy(filteredPassengerRides = emptyList())
        }

        Log.d("FILTER", "User dep=$departure arr=$arrival date=$date")
        session.listPassengerRides.forEach {
            Log.d("FILTER", "Ride ${it.idPassengerRide} | dep=${it.departureTerminalId} arr=${it.arrivalTerminalId}")
        }

        val filtered = session.listPassengerRides.filter { ride ->
            Log.d("FILTER", "Ride ${ride.idPassengerRide} | dep=${ride.departureTerminalId} arr=${ride.arrivalTerminalId} time=${ride.departureTime}")
            Log.d("FILTER", "Compare dep=${session.selectedDepartureTerminal.id}, arr=${session.selectedArrivalTerminal.id}, date=${session.selectedDate}")
            Log.d("FILTER PAGE 2", "Check ride ${ride.idPassengerRide}: dep=${ride.departureTerminalId}, arr=${ride.arrivalTerminalId}, date=${ride.departureTime}")

            ride.departureTerminalId == departure &&
                    ride.arrivalTerminalId == arrival &&
                    OffsetDateTime.parse(ride.departureTime).toLocalDate() == date
        }

        return session.copy(filteredPassengerRides = filtered)
    }

    /**
     * user pilih jadwal (belum booking)
     */
    fun selectRide(
        token: String,
        session: BookingSession,
        ride: PassengerRideCustomer,
        onUpdated: (BookingSession) -> Unit
    ) {
        Log.d("UI_PAGE3", "Interactor.selectRide() → rideId=${ride.idPassengerRide}")

        // 1. Save selected ride
        var updated = session.copy(selectedRide = ride)

        session.listPassengerPricing.forEach {
            Log.d("PRICING_DEBUG", "id=${it.id} dep=${it.departureTerminalId} arr=${it.arrivalTerminalId} vehicle=${it.vehicleType}")
        }


        // 2. Cari terminal departure & arrival dari session.listTerminals
        val departureTerminal = session.listTerminals
            .find { it.id == ride.departureTerminalId }
            ?: TerminalCustomer.empty()

        val arrivalTerminal = session.listTerminals
            .find { it.id == ride.arrivalTerminalId }
            ?: TerminalCustomer.empty()

        Log.d("UI_PAGE3", "Cari terminal dep=${departureTerminal.name}, arr=${arrivalTerminal.name}")

        updated = updated.copy(
            selectedDepartureTerminal = departureTerminal,
            selectedArrivalTerminal = arrivalTerminal
        )

        // 3. Cari passengerPricing berdasarkan terminal dep-arr
        val pricing = session.listPassengerPricing.find {
            it.departureTerminalId == ride.departureTerminalId &&
                    it.arrivalTerminalId == ride.arrivalTerminalId &&
                    it.vehicleType == session.vehicleType   // 🔒 KUNCI
        }

        Log.d("UI_PAGE3", "Pricing ditemukan: id=${pricing?.id} | price=${pricing?.pricePerSeat}")

        if (pricing == null) {
            Log.e("PRICING", "❌ Pricing not found for ${session.vehicleType}")
            return
        }

        updated = updated.copy(
            selectedPricing = pricing,
            totalPrice = pricing.pricePerSeat
        )

        Log.d("UI_PAGE3", "TotalPrice diset menjadi ${updated.totalPrice}")

        // 4. Change step
        updated = updated.copy(step = BookingStep.CONFIRM_PRICE)

        onUpdated(updated)
    }

    /**
     * user pilih payment
     */
    fun selectPaymentMethod(
        session: BookingSession,
        paymentMethod: PaymentMethodCustomer,
        onUpdated: (BookingSession) -> Unit
    ) {
        val updated = session.copy(
            selectedPaymentMethod = paymentMethod,
            step = BookingStep.CONFIRM_PRICE // tetap di halaman konfirmasi harga
        )

        onUpdated(updated)
    }

    /**
     * CREATE BOOKING ke backend
     */
    suspend fun confirmBooking(
        token: String,
        session: BookingSession,
        useCases: NebengMotorUseCases,
        onUpdated: (BookingSession) -> Unit,
        onError: (String) -> Unit
    ) {
        Log.d("UI_PAGE5", "=== CONFIRM BOOKING STARTED ===")
        Log.d("UI_PAGE5", "Selected Ride ID: ${session.selectedRide?.idPassengerRide}")
        Log.d("UI_PAGE5", "Selected Pricing: ${session.selectedPricing?.id}")
        Log.d("UI_PAGE5", "Total Price: ${session.totalPrice}")
        Log.d("UI_PAGE5", "Customer ID: ${session.customer?.idCustomer}")

        // Validasi
        val ride = session.selectedRide
        if (ride == null) {
            Log.e("UI_PAGE5", "Gagal confirmBooking → ride null")
            return onError("Ride belum dipilih")
        }

        val customer = session.customer
        if (customer == null) {
            Log.e("UI_PAGE5", "Gagal confirmBooking → customer null")
            return onError("Customer tidak ditemukan")
        }

        val pricing = session.selectedPricing
//        if (pricing == null) {
//            Log.e("UI_PAGE5", "Gagal confirmBooking → pricing null")
//            return onError("Pricing belum dipilih")
//        }

        // Request body booking
        val reqBooking = CreatePassengerRideBookingRequest(
            passengerRideId = ride.idPassengerRide,
            totalPrice = session.totalPrice,
            customerId = customer.idCustomer,
            seatsReserved = 1,
            status = PaymentStatus.PENDING.value
//            status = "pending"
        )

        Log.d("UI_PAGE5", "Send API CreatePassengerRideBookingRequest:")
        Log.d("UI_PAGE5", " → passengerRideId = ${reqBooking.passengerRideId}")
        Log.d("UI_PAGE5", " → customerId = ${reqBooking.customerId}")
        Log.d("UI_PAGE5", " → seatsReserved = ${reqBooking.seatsReserved}")
        Log.d("UI_PAGE5", " → totalPrice = ${reqBooking.totalPrice}")
        Log.d("UI_PAGE5", " → status = ${reqBooking.status}")

        // Panggil API
        useCases.createPassengerRideBooking(token, reqBooking).collect { result ->
            when (result) {
                is Result.Loading -> {
                    Log.d("UI_PAGE5", "Booking API: Loading...")
                }
                is Result.Error -> {
                    Log.e("UI_PAGE5", "Booking API Error: ${result.message}")
                    onError(result.message ?: "Gagal membuat booking")
                }
                is Result.Success -> {
                    Log.d("UI_PAGE5", "Booking API Success! Mapping response...")

                    val bookingSummary = result.data
                    val booking = bookingSummary.toPassengerRideBookingCustomer()

                    Log.d("UI_PAGE5", "Booking created with ID = ${booking.idBooking}")
                    Log.d("UI_PAGE5", "Booking status = ${booking.bookingStatus}")

                    val updated = session.copy(
                        booking = booking,
                        step = BookingStep.BOOKING_CREATED    // belum ke transaction dulu
                    )
                    onUpdated(updated)

                    // lanjut otomatis → create transaction
                    createTransactionAfterBooking(
                        token = token,
                        session = updated,
                        useCases = useCases,
                        onUpdated = onUpdated,
                        onError = onError
                    )
                }
            }
        }
    }

    /**
     * CREATE TRANSACTION ke backend
     */
    private suspend fun createTransactionAfterBooking(
        token: String,
        session: BookingSession,
        useCases: NebengMotorUseCases,
        onUpdated: (BookingSession) -> Unit,
        onError: (String) -> Unit
    ) {
        val booking = session.booking ?: return onError("Booking tidak ditemukan")
        val customer = session.customer ?: return onError("Customer null")
        val payment = session.selectedPaymentMethod ?: return onError("Payment method tidak dipilih")

        val request = CreatePassengerTransactionRequest(
            passengerRideBookingId = booking.idBooking,
            customerId = customer.idCustomer,
            totalAmount = session.totalPrice,
            paymentMethodId = payment.idPaymentMethod,
            paymentStatus = PaymentStatus.PENDING.value,     // BENAR, backend nanti overwrite setelah Midtrans settle
            creditUsed = 0,
            transactionDate = "",
            paymentProofImg = ""                 // fix
        )

        Log.d("UI_PAGE5", "Send API CreatePassengerTransactionRequest:")
        Log.d("UI_PAGE5", " → bookingId        = ${request.passengerRideBookingId}")
        Log.d("UI_PAGE5", " → customerId       = ${request.customerId}")
        Log.d("UI_PAGE5", " → totalAmount      = ${request.totalAmount}")
        Log.d("UI_PAGE5", " → paymentMethodId  = ${request.paymentMethodId}")
        Log.d("UI_PAGE5", " → paymentStatus    = ${request.paymentStatus}")
        Log.d("UI_PAGE5", " → creditUsed       = ${request.creditUsed}")
        Log.d("UI_PAGE5", " → transactionDate  = '${request.transactionDate}'")
        Log.d("UI_PAGE5", " → paymentProofImg  = '${request.paymentProofImg}'")

        useCases.createPassengerTransaction(token, request).collect { result ->
            when (result) {
                is Result.Loading -> {
                    Log.d("UI_PAGE5", "Transaction API: Loading...")
                }
                is Result.Error -> {
                    Log.e("UI_PAGE5", "Transaction API Error: ${result.message}")
                    onError(result.message ?: "Gagal membuat transaksi")
                }

                is Result.Success -> {
                    Log.d("UI_PAGE5", "Transaction API Success! Mapping response...")
                    val trx = result.data.toPassengerTransactionCustomer()
                    Log.d("UI_PAGE5", "Transaction created with ID = ${trx.idPassengerTransaction}")

                    val updated = session.copy(
                        transaction = trx,
                        step = BookingStep.WAITING_PAYMENT
                    )

                    onUpdated(updated)
                }
            }
        }
    }

    /**
     * Polling status transaksi BERBASIS paymentExpiredAt
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun monitorTransactionStatus(
        token: String,
        session: BookingSession,
        onUpdate: (BookingSession) -> Unit,
        pollIntervalMillis: Long = 20_000L,
        initialDelayMillis: Long = 5_000L
    ) {
        var trx = session.transaction ?: return

        Log.d(
            "PAGE 6 & 7 POOLING",
            "BEGIN POLLING trxId=${trx.idPassengerTransaction} expiredAt=${trx.paymentExpiredAt}"
        )

        delay(initialDelayMillis)

        while (true) {

            // ⛔ STOP JIKA EXPIRED
            trx.paymentExpiredAt
                ?.let { Instant.parse(it) }
                ?.takeIf { Instant.now().isAfter(it) }
                ?.let {
                    Log.d("PAGE 6 & 7 POOLING", "STOP POLLING → expired")
                    onUpdate(session.copy(step = BookingStep.FAILED))
                    return
                }

            Log.d(
                "PAGE 6 & 7 POOLING",
                "POLL → fetch transaction trxId=${trx.idPassengerTransaction}"
            )

            var shouldStop = false

            useCases
                .getByIdPassengerTransaction(token, trx.idPassengerTransaction)
                .collect { result ->

                    when (result) {

                        is Result.Loading -> Unit

                        is Result.Error -> {
                            Log.e(
                                "PAGE 6 & 7 POOLING",
                                "API ERROR → ${result.message}"
                            )
                        }

                        is Result.Success -> {
                            val latest =
                                result.data.toPassengerTransactionCustomer()

                            trx = latest

                            Log.d(
                                "PAGE 6 & 7 POOLING",
                                "API GET trxId=${trx.idPassengerTransaction} paymentStatus=${trx.paymentStatus}"
                            )

                            when (trx.paymentStatus) {

                                PaymentStatus.PENDING -> {
                                    onUpdate(
                                        session.copy(
                                            transaction = trx,
                                            step = BookingStep.WAITING_PAYMENT
                                        )
                                    )
                                }

                                PaymentStatus.DITERIMA,
                                PaymentStatus.CREDITED -> {
                                    onUpdate(
                                        session.copy(
                                            transaction = trx,
                                            step = BookingStep.PAYMENT_CONFIRMED
                                        )
                                    )
                                    shouldStop = true
                                }

                                PaymentStatus.DITOLAK,
                                PaymentStatus.EXPIRED -> {
                                    onUpdate(
                                        session.copy(
                                            transaction = trx,
                                            step = BookingStep.FAILED
                                        )
                                    )
                                    shouldStop = true
                                }
                            }
                        }
                    }
                }

            if (shouldStop) return

            delay(pollIntervalMillis)
        }
    }

    /**
     * YANG INI MASIH BELUM DIGUNAKAN TAPI HARUSNYA ADA KETIKA TELAH MELAKUKAN PEMBAYARAN & SELESAI POOLING DAN KETIKA DIREJECT DRIVER MAKA SEMUA PEMABAYARAN DIKEMBALIKAN
     * polling sampai driver accept
     */
    suspend fun observeRideProgress(
        token: String,
        session: BookingSession,
        onUpdate: (BookingSession) -> Unit,
        maxAttempts: Int = 30 // sekitar 30 × 3 detik = 90 detik
    ) {
        if (maxAttempts <= 0) return

        val booking = session.booking ?: run {
            onUpdate(session.copy(step = BookingStep.FAILED))
            return
        }

        val bookingId = booking.idBooking

        useCases.getByIdPassengerRideBooking(token, bookingId).collect { result ->
            when (result) {

                is Result.Loading -> Unit

                is Result.Error -> {
                    onUpdate(session.copy(step = BookingStep.FAILED))
                    return@collect
                }

                is Result.Success -> {
                    val latest = result.data.toPassengerRideBookingCustomer()
                    val bookingStatus = latest.bookingStatus

                    when (bookingStatus) {

                        // masih menunggu driver/backend
                        BookingStatus.PENDING -> {
                            onUpdate(
                                session.copy(
                                    booking = latest,
                                    step = BookingStep.WAITING_RIDE_ACCEPTANCE
                                )
                            )
                            delay(3000)
                            observeRideProgress(token, session.copy(booking = latest), onUpdate, maxAttempts - 1)
                        }

                        // berhasil diterima → perjalanan siap dilanjutkan
                        BookingStatus.DITERIMA -> {
                            onUpdate(
                                session.copy(
                                    booking = latest,
                                    step = BookingStep.RIDE_ACCEPTED
                                )
                            )
                        }

                        // booking gagal
                        BookingStatus.DITOLAK -> {
                            onUpdate(
                                session.copy(
                                    booking = latest,
                                    step = BookingStep.FAILED
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}