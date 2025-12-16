package com.example.nebeng.feature_a_homepage.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nebeng.R
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingStep
import com.example.nebeng.feature_a_homepage.presentation.HomepageViewModel
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.HomepageCustomerScreenUi
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.NebengMotorBookingViewModel
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_01.PassengerRideMotorScreen
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_02.PassengerRideMotorScheduleScreen
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_03.PassengerRideMotorScheduleDetailScreen
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_04.PassengerRideMotorPaymentMethodModel
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_04.PassengerRideMotorPaymentMethodScreen
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_05.PassengerRideMotorPaymentMethodDetailScreen
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_06.PassengerRideMotorPaymentStatusContainer
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_08.PassengerRideMotorPaymentSuccessScreen
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_09.PassengerRideMotorOnTheWayScreen
import com.example.nebeng.feature_a_homepage.presentation.screen_role.driver.HomepageDriverScreenUi
import com.example.nebeng.feature_a_homepage.presentation.screen_role.driver.nebeng_motor.DriverNebengMotorOnTheWayScreen

/**
 * ROLE CUSTOMER
 */
const val CUSTOMER_NEBENG_MOTOR                      = "customer/nebeng_motor"
const val CUSTOMER_NEBENG_MOTOR_RIDE_SCHEDULE        = "customer/nebeng_motor/ride_schedule"
const val CUSTOMER_NEBENG_MOTOR_RIDE_SCHEDULE_DETAIL = "customer/nebeng_motor/ride_schedule_detail"
const val CUSTOMER_NEBENG_MOTOR_PAYMENT_METHOD       = "customer/nebeng_motor/payment_method"
const val CUSTOMER_NEBENG_MOTOR_PAYMENT_METHOD_DETAIL= "customer/nebeng_motor/payment_method_detail"
const val CUSTOMER_NEBENG_MOTOR_PAYMENT_STATUS       = "customer/nebeng_motor/payment_status"
const val CUSTOMER_NEBENG_MOTOR_PAYMENT_SUCCESS      = "customer/nebeng_motor/payment_success"
const val CUSTOMER_NEBENG_MOTOR_ON_THE_WAY           = "customer/nebeng_motor/on_the_way"

/**
 * ROLE DRIVER (ONLY FOR SELF REALTIME OSM MONITORING)
 */
const val DRIVER_NEBENG_MOTOR_ON_THE_WAY             = "driver/nebeng_motor/on_the_way"
const val DRIVER_NEBENG_BARANG                       = "driver/nebeng_barang"


const val ROUTE_PASSENGER_MOTOR_MAP         = "passenger_motor_map"
const val ROUTE_PASSENGER_MOBIL             = "passenger_mobil"

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageNavHost(
    navController: NavHostController = rememberNavController(),
    userType: String,
    viewModel: HomepageViewModel,
    onRouteChanged: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val startDestination = if (userType.equals("driver", ignoreCase = true)) {
        "homepage_driver"
    } else {
        "homepage_customer"
    }

    // 🔥 ViewModel booking NEBENG MOTOR – DIAMBIL SEKALI DI SINI
    // Scoped ke Fragment / parent composable yang memanggil HomepageNavHost
    val bookingViewModel: NebengMotorBookingViewModel = hiltViewModel()

    // ⬇️ Listener untuk mendeteksi pergantian composable route
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            destination.route?.let { route ->
                onRouteChanged(route)   // ⬅️ kirim ke Fragment
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        /**
         * ROLE CUSTOMER
         */
        composable("homepage_customer") {
            HomepageCustomerScreenUi(
                state = uiState,
                onMenuMotorClick = {
                    navController.navigate(CUSTOMER_NEBENG_MOTOR)
                },
                onMenuOpenMapClick = {
                    navController.navigate(ROUTE_PASSENGER_MOTOR_MAP)  // 🔥 open map screen
                }
            )
        }

        // =========================
        // PAGE 01 — Nebeng Motor
        // =========================
        composable(CUSTOMER_NEBENG_MOTOR) {

            // ❌ JANGAN LAGI: val bookingViewModel: NebengMotorBookingViewModel = hiltViewModel()
            // ✅ Pakai instance yang diambil di atas (shared)
            val session = bookingViewModel.session.collectAsStateWithLifecycle().value
            val loading = bookingViewModel.loading.collectAsStateWithLifecycle().value
            val error = bookingViewModel.error.collectAsStateWithLifecycle().value

            // load initial ketika pertama kali masuk
            LaunchedEffect(Unit) {
                val customer = uiState.currentUser ?: run {
                    Log.e("NEBENG_MOTOR", "❌ currentUser = null → auth belum tersimpan di datastore")
                    return@LaunchedEffect
                }

                Log.d(
                    "NEBENG_MOTOR",
                    """
                    🚀 START CALLED
                    ---------------------------
                    userId       = ${customer.userId}
                    customerId   = ${customer.customerId}
                    name         = ${customer.name}
                    username     = ${customer.username}
                    token prefix = ${customer.token.take(15)}...
                    ---------------------------
                    """.trimIndent()
                )

                bookingViewModel.start(
                    token = customer.token,
                    customerId = customer.customerId
                )
            }

            PassengerRideMotorScreen(
                session = session,
                loading = loading,
                error = error,
                terminals = session.listTerminals,
                onBack = { navController.popBackStack() },

                onSelectStartLocation = { bookingViewModel.setStartLocation(it) },
                onSelectEndLocation = { bookingViewModel.setEndLocation(it) },
                onSelectDate = { bookingViewModel.setDepartureDate(it) },

                onNext = { navController.navigate(CUSTOMER_NEBENG_MOTOR_RIDE_SCHEDULE) }
            )
        }

        // =========================
        // PAGE 02 — Jadwal Nebeng Motor
        // =========================
        composable(CUSTOMER_NEBENG_MOTOR_RIDE_SCHEDULE) {

            // ❌ JANGAN LAGI: val bookingViewModel: NebengMotorBookingViewModel = hiltViewModel()
            // ✅ Pakai instance shared
            val session = bookingViewModel.session.collectAsStateWithLifecycle().value

            Log.d("FILTER", "📄 PAGE02 masuk. filteredRides = ${session.filteredPassengerRides.size}")
            Log.d("FILTER_UI PAGE 2", "Page02 → filteredRide size=${session.filteredPassengerRides.size}")
            session.filteredPassengerRides.forEach {
                Log.d("FILTER_UI PAGE 2", "UI Ride ${it.idPassengerRide} dep=${it.departureTerminalId} arr=${it.arrivalTerminalId}")
            }

            PassengerRideMotorScheduleScreen(
                session = session,
                rides = session.filteredPassengerRides,
                onBack = { navController.popBackStack() },
                onDetailClick = { rideId ->
                    // nanti kalau sudah ada selectRide(ride) bisa kirim id / object
                    navController.navigate(CUSTOMER_NEBENG_MOTOR_RIDE_SCHEDULE_DETAIL)
                },
                onSelectRide = { ride ->
                    bookingViewModel.selectRide(ride)
                }
            )
        }

        // PAGE 03
        composable(CUSTOMER_NEBENG_MOTOR_RIDE_SCHEDULE_DETAIL) {
            val session = bookingViewModel.session.collectAsStateWithLifecycle().value

            // ========= MEDIUM LOG PAGE 3 ===========
            Log.d("UI_PAGE3", "=== MASUK PAGE 3 ===")
            Log.d("UI_PAGE3", "Selected Ride: ${session.selectedRide?.idPassengerRide}")
            Log.d("UI_PAGE3", "Departure Terminal: ${session.selectedDepartureTerminal?.id} | ${session.selectedDepartureTerminal?.name}")
            Log.d("UI_PAGE3", "Arrival Terminal: ${session.selectedArrivalTerminal?.id} | ${session.selectedArrivalTerminal?.name}")
            Log.d("UI_PAGE3", "Customer: ${session.customer?.customerName} (${session.customer?.idCustomer})")
            Log.d("UI_PAGE3", "Pricing: ${session.selectedPricing?.id} | Rp.${session.selectedPricing?.pricePerSeat}")
            Log.d("UI_PAGE3", "Total Price: ${session.totalPrice}")

            PassengerRideMotorScheduleDetailScreen(
                session = session,
                onBack = { navController.popBackStack() },
                onPay = { navController.navigate(CUSTOMER_NEBENG_MOTOR_PAYMENT_METHOD) }
            )
        }

        // PAGE 04
        composable(CUSTOMER_NEBENG_MOTOR_PAYMENT_METHOD) {
            val session = bookingViewModel.session.collectAsStateWithLifecycle().value

            // ===== MEDIUM LOG =====
            Log.d("UI_PAGE4", "=== MASUK PAGE 4 ===")
            Log.d("UI_PAGE4", "Available Payment Methods = ${session.listPaymentMethods.size}")
            session.listPaymentMethods.forEach {
                Log.d("UI_PAGE4", "Method: ${it.idPaymentMethod} | ${it.bankName}")
            }

            PassengerRideMotorPaymentMethodScreen (
                paymentMethods = session.listPaymentMethods,

                onBack = { navController.popBackStack() },

                onSelect = { method ->
                    Log.d("UI_PAGE4", "User pilih payment: ${method.bankName}")
                    bookingViewModel.selectPaymentMethod(method)
                },

                onNext = {
                    val selected = session.selectedPaymentMethod
                    Log.d("UI_PAGE4", "Next clicked → selected=${selected?.bankName}")

                    if (selected != null) {
                        navController.navigate(CUSTOMER_NEBENG_MOTOR_PAYMENT_METHOD_DETAIL)
                    } else {
                        Log.e("UI_PAGE4", "❌ Payment method belum dipilih")
                    }
                }
            )
        }

        // PAGE 05
        // PAGE 05 — PAYMENT METHOD DETAIL (EXECUTION POINT)
        composable(CUSTOMER_NEBENG_MOTOR_PAYMENT_METHOD_DETAIL) {

            val session = bookingViewModel.session.collectAsStateWithLifecycle().value
            val loading = bookingViewModel.loading.collectAsStateWithLifecycle().value
            val error   = bookingViewModel.error.collectAsStateWithLifecycle().value

            // ---------- MEDIUM LOG ----------
            Log.d("UI_PAGE5", "=== MASUK PAGE 5 ===")
            Log.d("UI_PAGE5", "RideId       = ${session.selectedRide?.idPassengerRide}")
            Log.d("UI_PAGE5", "CustomerId   = ${session.customer?.idCustomer}")
            Log.d("UI_PAGE5", "PaymentId    = ${session.selectedPaymentMethod?.idPaymentMethod}")
            Log.d("UI_PAGE5", "TotalPrice   = ${session.totalPrice}")

            PassengerRideMotorPaymentMethodDetailScreen(
                onBack = { navController.popBackStack() },

                onPay = {
                    Log.d("UI_PAGE5", "💥 USER CLICK PAY → confirmBooking()")
                    bookingViewModel.confirmBooking()
                },

                orderNumber = "FR-${session.selectedRide?.idPassengerRide ?: "-"}",

                paymentMethod = PassengerRideMotorPaymentMethodModel(
                    name = session.selectedPaymentMethod?.bankName ?: "Unknown",
                    icon = R.drawable.qris
                ),

                totalAmount = "Rp ${session.totalPrice}"
            )

            // ---------- NAVIGASI SETELAH STATE BERUBAH ----------
            LaunchedEffect(session.step) {
                when (session.step) {

                    BookingStep.WAITING_PAYMENT -> {
                        Log.d("UI_PAGE5", "➡️ Booking & Transaction created → WAITING_PAYMENT")
                        navController.navigate(CUSTOMER_NEBENG_MOTOR_PAYMENT_STATUS) {
                            popUpTo(CUSTOMER_NEBENG_MOTOR_PAYMENT_METHOD_DETAIL) { inclusive = true }
                        }
                    }

                    BookingStep.FAILED -> {
                        Log.e("UI_PAGE5", "❌ Booking failed: $error")
                    }

                    else -> Unit
                }
            }

            // ----- Optional: Loading Overlay -----
            if (loading) {
                Log.d("UI_PAGE5", "⏳ Loading confirmBooking()")
            }
        }

        // PAGE 06 & PAGE 07 (IT JUST SAME BUT DIFFERENT UI MODE)
        composable(CUSTOMER_NEBENG_MOTOR_PAYMENT_STATUS) {
            val session = bookingViewModel.session.collectAsStateWithLifecycle().value

            /**
             * ✅ START POLLING
             * Dipicu HANYA saat masuk WAITING_PAYMENT
             */
            LaunchedEffect(session.step) {
                if (session.step == BookingStep.WAITING_PAYMENT) {
                    bookingViewModel.startMonitorTransaction()
                }
            }

            /**
             * ✅ AUTO NAV KE SUCCESS
             * Reaksi murni terhadap state machine
             */
            LaunchedEffect(session.step) {
                when (session.step) {
                    BookingStep.PAYMENT_CONFIRMED -> {
                        Log.d(
                            "PAGE 6 & 7 POOLING",
                            "NAV → PAYMENT_CONFIRMED"
                        )

                        navController.navigate(CUSTOMER_NEBENG_MOTOR_PAYMENT_SUCCESS) {
                            popUpTo(CUSTOMER_NEBENG_MOTOR_PAYMENT_STATUS) { inclusive = true }
                        }
                    }

                    BookingStep.FAILED -> {
                        // nanti bisa diarahkan ke halaman gagal
                        Log.e(
                            "PAGE 6 & 7 POOLING",
                            "NAV → FAILED | paymentStatus=${session.transaction?.paymentStatus}"
                        )
                    }

//                    else -> Unit
                    else -> {
                        Log.d(
                            "PAGE 6 & 7 POOLING",
                            "NAV idle step=${session.step}"
                        )
                    }
                }
            }

            PassengerRideMotorPaymentStatusContainer(
                session = session,
                onBack = {
                    // optional: konfirmasi keluar
                    navController.popBackStack()
                },
                onSwitchMode = { mode ->
                    bookingViewModel.setPaymentUiMode(mode)
                }
            )
        }


        // PAGE 08
        composable(CUSTOMER_NEBENG_MOTOR_PAYMENT_SUCCESS) {
            val session = bookingViewModel.session.collectAsStateWithLifecycle().value

            LaunchedEffect(Unit) {
                bookingViewModel.startObserveRideProgress()
            }

            PassengerRideMotorPaymentSuccessScreen(
                session = session,
                onBack = { navController.popBackStack() },
                onNext = {
                    navController.navigate(CUSTOMER_NEBENG_MOTOR_ON_THE_WAY) {
                        popUpTo(CUSTOMER_NEBENG_MOTOR_PAYMENT_SUCCESS) { inclusive = true }
                    }
                }
            )
        }

        // PAGE 09
        composable(CUSTOMER_NEBENG_MOTOR_ON_THE_WAY) {
            val session = bookingViewModel.session.collectAsStateWithLifecycle().value
            PassengerRideMotorOnTheWayScreen(
                session = session,
                onBack = { navController.popBackStack() },
                onCancelOrder = { navController.navigate(CUSTOMER_NEBENG_MOTOR_PAYMENT_METHOD) }
            )
        }

        // 🔥 ROUTE MAP WAJIB ADA
//        composable(ROUTE_PASSENGER_MOTOR_MAP) {
//            // sementara pakai screen on the way
//            PassengerRideMotorOnTheWayScreen()
//        }


        /**
         * ROLE DRIVER
         */

        // ==== DRIVER FLOW (masih draft) ====
        composable("homepage_driver") {
            // HomepageDriverScreenUi(... nanti)
            HomepageDriverScreenUi(
                state = uiState,
                onMenuMotorClick = {
                    navController.navigate(DRIVER_NEBENG_MOTOR_ON_THE_WAY)
                },
                onMenuBarangClick = {
                    navController.navigate(DRIVER_NEBENG_BARANG)
                }
            )
        }

//        composable(DRIVER_NEBENG_MOTOR_ON_THE_WAY) {
//            // nanti DriverNebengMotorOnTheWayScreen()
////            Text("Driver OSM Tracking (coming soon)")
//
//        }
        // =========================
        // DRIVER — NEBENG MOTOR (ON THE WAY)
        // =========================
        composable(DRIVER_NEBENG_MOTOR_ON_THE_WAY) {
            DriverNebengMotorOnTheWayScreen(
                onBack = {
                    navController.popBackStack()
                },
                onFinishRide = {
                    // sementara kosong (UI-only)
                    // nanti bisa navigate ke halaman selesai / riwayat
                }
            )
        }


        composable("verify_documents") {
            Text("Halaman Verifikasi Dokumen (coming soon)")
        }
    }
}