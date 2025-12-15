package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_a_homepage.domain.interactor.customer.nebeng_motor.NebengMotorBookingInteractor
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerRideCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PaymentMethodCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.TerminalCustomer
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingStep
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.PaymentUiMode
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_01.bottom_sheet.LocationUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

@HiltViewModel
class NebengMotorBookingViewModel @Inject constructor(
    private val interactor: NebengMotorBookingInteractor
) : ViewModel() {

    private val _session = MutableStateFlow(BookingSession())
    val session: StateFlow<BookingSession> = _session.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var token: String = ""
    private var customerId: Int = -1

    private var monitorTransactionJob: Job? = null

    private var isMonitoringTransaction = false

    fun loadTerminalsIfNeeded() {
        if (session.value.listTerminals.isNotEmpty()) return  // sudah pernah load

        viewModelScope.launch {
            _loading.value = true

            when (val res = interactor.loadTerminals(token)) {
                is Result.Success -> _session.update {
                    it.copy(listTerminals = res.data)
                }
                is Result.Error -> _error.value = res.message
                else -> Unit
            }
            _loading.value = false
        }
    }

    /** 📌 dipanggil pertama kali saat halaman NebengMotor dibuka */
    fun start(token: String, customerId: Int) {
        this.token = token
        this.customerId = customerId

        viewModelScope.launch {
            _loading.value = true

            when (val res = interactor.loadInitial(token, customerId)) {
                is Result.Success -> _session.value = res.data
                is Result.Error -> _error.value = res.message
                else -> Unit
            }

            _loading.value = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun applyRideFilter() {
        Log.d("FILTER", "applyRideFilter() called")
        Log.d("FILTER", "Current session dep=${_session.value.selectedDepartureTerminal?.id} arr=${_session.value.selectedArrivalTerminal?.id} date=${_session.value.selectedDate}")

        Log.d("FILTER PAGE 2", "Total rides before filter: ${_session.value.listPassengerRides.size}")
        _session.value.listPassengerRides.forEach {
            Log.d("FILTER PAGE 2", "Ride ${it.idPassengerRide} dep=${it.departureTerminalId} arr=${it.arrivalTerminalId}")
        }

        _session.update { old ->
            interactor.filterRides(old)
        }

        Log.d("FILTER_RESULT", "After filter: found=${_session.value.filteredPassengerRides.size}")
    }

    /** 📌 user klik card ride */
    fun selectRide(ride: PassengerRideCustomer) {
        viewModelScope.launch {
            interactor.selectRide(
                token = token,
                session = _session.value,
                ride = ride,
                onUpdated = { updated ->
                    Log.d("UI_PAGE3", "Session updated → selectedRide=${updated.selectedRide?.idPassengerRide}")
                    Log.d("UI_PAGE3", "Pricing terpilih: ${updated.selectedPricing?.pricePerSeat}")
                    Log.d("UI_PAGE3", "Total Price: ${updated.totalPrice}")
                    _session.value = updated
                }
            )
        }
    }


    /** 📌 user klik radio button payment */
    fun selectPaymentMethod(payment: PaymentMethodCustomer) {
        interactor.selectPaymentMethod(
            session = _session.value,
            paymentMethod = payment,
            onUpdated = { updated -> _session.value = updated }
        )
    }


    /** 📌 user klik tombol “Bayar / Book” */
    fun confirmBooking() {
        viewModelScope.launch {
            Log.d("UI_PAGE5", "User menekan tombol KONFIRMASI PEMBAYARAN")
            Log.d("UI_PAGE5", "Session sebelum create booking:")
            Log.d("UI_PAGE5", "customerId=${_session.value.customer?.idCustomer}")
            Log.d("UI_PAGE5", "rideId=${_session.value.selectedRide?.idPassengerRide}")
            Log.d("UI_PAGE5", "pricingId=${_session.value.selectedPricing?.id}")
            Log.d("UI_PAGE5", "paymentMethodId=${_session.value.selectedPaymentMethod?.idPaymentMethod}")
            Log.d("UI_PAGE5", "totalPrice=${_session.value.totalPrice}")

            Log.d("PAGE 6 & 7 POOLING", "USER CLICK PAY → confirmBooking()")

            _loading.value = true

            interactor.confirmBooking(
                token = token,
                session = _session.value,
                useCases = interactor.useCases, // karena interactor memerlukan useCases
                onUpdated = { updated ->
                    Log.d(
                        "PAGE 6 & 7 POOLING",
                        "BOOKING UPDATED | step=${updated.step} " +
                                "trxId=${updated.transaction?.idPassengerTransaction}"
                    )

                    Log.d("UI_PAGE5", "Booking berhasil → bookingId=${updated.booking?.idBooking}")
                    if (updated.transaction != null) {
                        Log.d("UI_PAGE5", "Transaction berhasil → transactionId=${updated.transaction?.idPassengerTransaction}")
                    } else {
                        Log.d("UI_PAGE5", "Transaction belum terbentuk (masih proses / gagal).")
                    }

                    _session.value = updated
                },
                onError = { message ->
                    Log.e("PAGE 6 & 7 POOLING", "BOOKING ERROR = $message")
                    Log.e("UI_PAGE5", "Gagal melakukan booking: $message")
                    _error.value = message
                }
            )

            _loading.value = false
        }
    }

    /** 📌 mulai polling pembayaran */
    @RequiresApi(Build.VERSION_CODES.O)
    fun startMonitorTransaction() {
        Log.d(
            "PAGE 6 & 7 POOLING",
            "START monitor | step=${_session.value.step} " +
                    "trxId=${_session.value.transaction?.idPassengerTransaction}"
        )


        if (isMonitoringTransaction) {
            Log.d("PAGE 6 & 7 POOLING", "⏭️ Monitor already running, skip")
            return
        }

        val currentSession = _session.value
        val trxId = currentSession.transaction?.idPassengerTransaction

        if (trxId == null) {
            Log.e("PAGE 6 & 7 POOLING", "❌ trxId NULL → cannot start monitor")
            return
        }

        Log.d("VM_MONITOR", "✅ Start monitorTransaction trxId=$trxId")
        isMonitoringTransaction = true

        viewModelScope.launch {
            interactor.monitorTransactionStatus(
                token = token,
                session = currentSession,
                onUpdate = { updated ->
                    _session.value = updated

                    Log.d(
                        "PAGE 6 & 7 POOLING",
                        "MONITOR UPDATE | step=${updated.step} " +
                                "paymentStatus=${updated.transaction?.paymentStatus}"
                    )

                    // 🔥 STOP GUARD JIKA STATUS FINAL
                    if (
                        updated.step == BookingStep.PAYMENT_CONFIRMED ||
                        updated.step == BookingStep.FAILED
                    ) {
                        Log.d("VM_MONITOR", "🛑 Monitor selesai, release guard")
                        Log.d(
                            "PAGE 6 & 7 POOLING",
                            "STOP MONITOR | finalStep=${updated.step}"
                        )
                        isMonitoringTransaction = false
                    }
                }
            )
        }
    }


    /** 📌 mulai polling bookingStatus menunggu driver accept */
    fun startObserveRideProgress() {
        viewModelScope.launch {
            interactor.observeRideProgress(
                token = token,
                session = _session.value,
                onUpdate = { updated -> _session.value = updated }
            )
        }
    }

    /** 📌 reset setelah selesai (berhasil / gagal) */
    fun reset() {
        monitorTransactionJob?.cancel()
        monitorTransactionJob = null

        _session.value = BookingSession()
        _error.value = null
        _loading.value = false

        isMonitoringTransaction = false
    }


    /** ---------------------------------------------------------------------
     *  SETTER UNTUK TERMINAL (domain TerminalCustomer)
     * ---------------------------------------------------------------------- */
    @RequiresApi(Build.VERSION_CODES.O)
    fun setDeparture(terminal: TerminalCustomer) {
        _session.update { it.copy(selectedDepartureTerminal = terminal) }
        applyRideFilter()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setArrival(terminal: TerminalCustomer) {
        _session.update { it.copy(selectedArrivalTerminal = terminal) }
        applyRideFilter()
    }

    /** ---------------------------------------------------------------------
     *  SET SELECTED DATE
     * ---------------------------------------------------------------------- */
    @RequiresApi(Build.VERSION_CODES.O)
    fun setDepartureDate(date: LocalDate) {
        _session.update { it.copy(selectedDate = date) }
        applyRideFilter()
    }

    /** ---------------------------------------------------------------------
     *  SET SELECTED DATE
     * ---------------------------------------------------------------------- */
    fun onSelectDate(date: LocalDate) {
        _session.update { it.copy(selectedDate = date) }
    }

    /** ---------------------------------------------------------------------
     *  FROM UI → DOMAIN MAPPING
     * ---------------------------------------------------------------------- */
    @RequiresApi(Build.VERSION_CODES.O)
    fun setStartLocation(ui: LocationUiModel) {
        ui.rawTerminal?.let {
            Log.d("SELECT_DEP", "User pilih departure id=${ui.rawTerminal?.id} name=${ui.rawTerminal?.name}")
            setDeparture(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setEndLocation(ui: LocationUiModel) {
            Log.d("SELECT_ARR", "User pilih arrival id=${ui.rawTerminal?.id} name=${ui.rawTerminal?.name}")
            ui.rawTerminal?.let { setArrival(it)
        }
    }

    /**
     * PAYMENT PAGE MODE (PAGE 6 & 7) -> as a unity
     */
    fun setPaymentUiMode(mode: PaymentUiMode) {
        _session.update { it.copy(paymentUiMode = mode) }
    }
}
