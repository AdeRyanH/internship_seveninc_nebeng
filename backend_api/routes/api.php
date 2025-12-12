<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\RideController;
use App\Http\Controllers\UserController;
use App\Http\Controllers\DriverController;
use App\Http\Controllers\RatingController;
use App\Http\Controllers\RefundController;
use App\Http\Controllers\RegencyController;
use App\Http\Controllers\VehicleController;
use App\Http\Controllers\CustomerController;
use App\Http\Controllers\DistrictController;
use App\Http\Controllers\ProvinceController;
use App\Http\Controllers\TerminalController;
use App\Http\Controllers\GoodsRideController;
use App\Http\Controllers\AdminOrdersController;
use App\Http\Controllers\DriverAdminController;
use App\Http\Controllers\TransactionController;
use App\Http\Controllers\VehicleAdminController;
use App\Http\Controllers\AdministratorController;
use App\Http\Controllers\CustomerAdminController;
use App\Http\Controllers\PassengerRideController;
use App\Http\Controllers\PaymentMethodController;
use App\Http\Controllers\CreditScoreLogController;
use App\Http\Controllers\DriverCommissionController;
use App\Http\Controllers\DriverWithdrawalController;
use App\Http\Controllers\GoodsRideBookingController;
use App\Http\Controllers\GoodsTransactionController;
use App\Http\Controllers\PassengerPricingController;
use App\Http\Controllers\DriverLocationGoodController;
use App\Http\Controllers\DriverLocationRideController;
use App\Http\Controllers\PassengerRideBookingController;
use App\Http\Controllers\PassengerTransactionController;

Route::post('/register', [AuthController::class, 'register']);
Route::post('/login', [AuthController::class, 'login']);
Route::post('/administrator', [AdministratorController::class, 'store']);

Route::middleware('auth:api')->group(function() {

    // [PRIVATE ROUTE]
    Route::post('/logout', [AuthController::class, 'logout']);

    // [ TERMINAL ]
    Route::prefix('terminals')->group(function () {
        Route::get('/', [TerminalController::class, 'index']);
        Route::get('/{id}', [TerminalController::class, 'show']);
        Route::post('/', [TerminalController::class, 'store']);
        Route::put('/{id}', [TerminalController::class, 'update']);
        Route::delete('/{id}', [TerminalController::class, 'destroy']);
    });

    // [PROVINCE]
    Route::prefix('provinces')->group(function () {
        Route::get('/', [ProvinceController::class, 'index']);
        Route::get('/{id}', [ProvinceController::class, 'show'] );

        // [ REGENCY BY PROVINCE ]
        Route::get('/{province_id}/regencies', [RegencyController::class, 'getByProvince']);
    });

    // [REGENCY]
    Route::prefix('regencies')->group(function(){
        Route::get('/', [RegencyController::class, 'index']);
        Route::get('{id}', [RegencyController::class, 'show']);

        // [ DISTRICT BY REGENCY ]
        Route::get('/{regency_id}/districts', [DistrictController::class, 'getByRegency']);
    });

    // [DISTRICT]
    Route::prefix('districts')->group(function () {
        Route::get('/', [DistrictController::class, 'index']);
        Route::get('/{id}', [DistrictController::class, 'show']);
    });

    // [GOODS TRANSACTION]
    Route::prefix('goods-transactions')->group(function () {
        Route::get('/', [GoodsTransactionController::class, 'index']);
        Route::get('/{id}', [GoodsTransactionController::class, 'show']);
        Route::post('/', [GoodsTransactionController::class, 'store']);
        Route::put('/{id}', [GoodsTransactionController::class, 'update']);
        Route::delete('/{id}', [GoodsTransactionController::class, 'destroy']);
    });

    // [GOODS RIDE]
    Route::prefix('goods-rides')->group(function () {
        Route::get('/', [GoodsRideController::class, 'index']);
        Route::get('/{id}', [GoodsRideController::class, 'show']);
        Route::post('/', [GoodsRideController::class, 'store']);
        Route::put('/{id}', [GoodsRideController::class, 'update']);
        Route::delete('/{id}', [GoodsRideController::class, 'destroy']);
    });

    // [ORDERS]
    Route::prefix('orders')->group(function(){
        Route::get('/',[AdminOrdersController::class, 'index']);
        Route::get('/{type}/{id}',[AdminOrdersController::class, 'show']);
    });


    //  [RIDE]
    Route::prefix('ride')->group(function(){
        Route::get('/', [RideController::class, 'index']);
        Route::get('/driver/{driverId}', [RideController::class, 'byDriver']);
        Route::get('/status/{status}', [RideController::class, 'byStatus']);
        Route::get('/{type}/{id}', [RideController::class, 'show']);
    });
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################

    // [ DRIVER ]
    Route::prefix('drivers')->group(function () {
        Route::get('/', [DriverController::class, 'index']);
        Route::get('/{id}', [DriverController::class, 'show']);
        Route::post('/', [DriverController::class, 'store']);
        Route::put('/{id}', [DriverController::class, 'update']);
        Route::patch('{id}/verify', [DriverController::class, 'verify']);
        Route::delete('/{id}', [DriverController::class, 'destroy']);
    });

    // [ RATING ]
    Route::prefix('ratings')->group(function () {
        Route::get('/', [RatingController::class, 'index']);
        Route::get('/{id}', [RatingController::class, 'show']);
        Route::get('/driver/{driver_id}', [RatingController::class, 'showByDriver']);
        Route::post('/', [RatingController::class, 'store']);
        Route::put('/{id}', [RatingController::class, 'update']);
        Route::delete('/{id}', [RatingController::class, 'destroy']);
    });

    // [ GOODS RIDE BOOKINGS ]
    Route::prefix('goods-ride-bookings')->group(function () {
        Route::get('/', [GoodsRideBookingController::class, 'index']);
        Route::get('/{id}', [GoodsRideBookingController::class, 'show']);
        Route::get('/driver/{driver_id}', [GoodsRideBookingController::class, 'byDriver']);
        Route::get('/status/{status}', [GoodsRideBookingController::class, 'byStatus']);
        Route::post('/', [GoodsRideBookingController::class, 'store']);
        Route::put('/{id}', [GoodsRideBookingController::class, 'update']);
        Route::delete('/{id}', [GoodsRideBookingController::class, 'destroy']);
    });

    // [ PASSENGER RIDE ]
    Route::prefix('passenger-rides')->group(function () {
        Route::get('/', [PassengerRideController::class, 'index']);
        Route::get('/{id}', [PassengerRideController::class, 'show']);
        Route::get('/driver/{driver_id}', [PassengerRideController::class, 'byDriver']);
        Route::get('/status/{status}', [PassengerRideController::class, 'byStatus']);
        Route::post('/', [PassengerRideController::class, 'store']);
        Route::put('/{id}', [PassengerRideController::class, 'update']);
        Route::delete('/{id}', [PassengerRideController::class, 'destroy']);
    });

    // [ CREDIT SCORE LOG ]
    Route::prefix('credit-score-logs')->group(function () {
        Route::get('/', [CreditScoreLogController::class, 'index']);
        Route::get('/{id}', [CreditScoreLogController::class, 'show']);
        Route::get('/driver/{driver_id}', [CreditScoreLogController::class, 'byDriver']);
        Route::post('/', [CreditScoreLogController::class, 'store']);
        Route::put('/{id}', [CreditScoreLogController::class, 'update']);
        Route::delete('/{id}', [CreditScoreLogController::class, 'destroy']);
    });

    // [ DRIVER WITHDRAWALS ]
    Route::prefix('driver-withdrawals')->group(function () {
        Route::get('/', [DriverWithdrawalController::class, 'index']);
        Route::get('/{id}', [DriverWithdrawalController::class, 'show']);
        Route::get('/driver/{driver_id}', [DriverWithdrawalController::class, 'byDriver']);
        Route::get('/status/{status}', [DriverWithdrawalController::class, 'byStatus']);
        Route::post('/', [DriverWithdrawalController::class, 'store']);
        Route::put('/{id}', [DriverWithdrawalController::class, 'update']);
        Route::delete('/{id}', [DriverWithdrawalController::class, 'destroy']);
    });

    // [ DRIVER COMMISSIONS ]
    Route::prefix('driver-commissions')->group(function () {
        Route::get('/', [DriverCommissionController::class, 'index']);
        Route::get('/{id}', [DriverCommissionController::class, 'show']);
        Route::get('/driver/{driver_id}', [DriverCommissionController::class, 'byDriver']);
        Route::post('/', [DriverCommissionController::class, 'store']);
        Route::put('/{id}', [DriverCommissionController::class, 'update']);
        Route::delete('/{id}', [DriverCommissionController::class, 'destroy']);
    });

    // [ VEHICLES ]
    Route::prefix('vehicles')->group(function () {
        Route::get('/', [VehicleController::class, 'index']);
        Route::get('/{id}', [VehicleController::class, 'show']);
        Route::get('/driver/{driver_id}', [VehicleController::class, 'byDriver']);
        Route::post('/', [VehicleController::class, 'store']);
        Route::put('/{id}', [VehicleController::class, 'update']);
        Route::patch('/{id}/verify', [VehicleController::class, 'verify']);
        Route::patch('/{id}/reject', [VehicleController::class, 'reject']);
        Route::delete('/{id}', [VehicleController::class, 'destroy']);
    });



    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################




    // [ CUSTOMERS ]
    Route::prefix('customers')->group(function () {
        Route::get('/', [CustomerController::class, 'index']);
        Route::get('/{id}', [CustomerController::class, 'show']);
        Route::get('/user/{user_id}', [CustomerController::class, 'byUser']);
        Route::post('/', [CustomerController::class, 'store']);
        Route::put('/{id}', [CustomerController::class, 'update']);
        Route::patch('/{id}/verify', [CustomerController::class, 'verify']);
        Route::patch('/{id}/add-credit', [CustomerController::class, 'addCredit']);
        Route::patch('/{id}/deduct-credit', [CustomerController::class, 'deductCredit']);
        Route::delete('/{id}', [CustomerController::class, 'destroy']);
    });

    // [ TRANSACTION ]
    Route::prefix('transactions')->group(function(){
        Route::get('/',[ TransactionController::class, 'index']);
        Route::get('/{type}/{id}',[TransactionController::class, 'show']);
        Route::get('/{type}/booking/{BookingId}',[TransactionController::class, 'getByBooking']);
    });

    // [ PASSENGER TRANSACTIONS ]
    Route::prefix('passenger-transactions')->group(function () {
        Route::get('/', [PassengerTransactionController::class, 'index']);
        Route::get('/{id}', [PassengerTransactionController::class, 'show']);
        Route::get('/customer/{customer_id}', [PassengerTransactionController::class, 'byCustomer']);
        Route::get('/booking/{booking_id}', [PassengerTransactionController::class, 'byBooking']);
        Route::post('/', [PassengerTransactionController::class, 'store']);
        Route::put('/{id}', [PassengerTransactionController::class, 'update']);
        Route::patch('/{id}/status', [PassengerTransactionController::class, 'updateStatus']);
        Route::delete('/{id}', [PassengerTransactionController::class, 'destroy']);
    });

    // [ PAYMENT METHODS ]
    Route::prefix('payment-methods')->group(function () {
        Route::get('/', [PaymentMethodController::class, 'index']);
        Route::get('/{id}', [PaymentMethodController::class, 'show']);
        Route::post('/', [PaymentMethodController::class, 'store']);
        Route::put('/{id}', [PaymentMethodController::class, 'update']);
        Route::delete('/{id}', [PaymentMethodController::class, 'destroy']);
    });

    // [ PASSENGER RIDE BOOKINGS ]
    Route::prefix('passenger-ride-bookings')->group(function () {
        Route::get('/', [PassengerRideBookingController::class, 'index']);
        Route::get('/{id}', [PassengerRideBookingController::class, 'show']);
        Route::get('/ride/{ride_id}', action: [PassengerRideBookingController::class, 'byRide']);
        Route::get('/customer/{customer_id}', [PassengerRideBookingController::class, 'byCustomer']);
        Route::post('/', [PassengerRideBookingController::class, 'store']);
        Route::put('/{id}', [PassengerRideBookingController::class, 'update']);
        Route::patch('/{id}/status', [PassengerRideBookingController::class, 'updateStatus']);
        Route::delete('/{id}', [PassengerRideBookingController::class, 'destroy']);
    });

    // [ PASSENGER PRICING ]
    Route::prefix('passenger-pricings')->group(function () {
        Route::get('/', [PassengerPricingController::class, 'index']);
        Route::get('/{id}', [PassengerPricingController::class, 'show']);
        Route::post('/', [PassengerPricingController::class, 'store']);
        Route::put('/{id}', [PassengerPricingController::class, 'update']);
        Route::delete('/{id}', [PassengerPricingController::class, 'destroy']);
    });

    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################

    // [ REALTIME DRIVER LOCATION ]
    Route::prefix('location-rides')->group(function () {
        Route::post('/{ride_id}/location', [DriverLocationRideController::class, 'store']);
        Route::get('/{ride_id}/location', [DriverLocationRideController::class, 'show']);
    });

    Route::prefix('location-goods')->group(function () {
        Route::post('/{ride_id}/location', [DriverLocationGoodController::class, 'store']);
        Route::get('/{ride_id}/location', [DriverLocationGoodController::class, 'show']);
    });

    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################

    //  [ ADMINISTRATOR ]
    Route::prefix('administrator')->group(function() {
        Route::get('/', [AdministratorController::class, 'index']);
        Route::get('/{id}', [AdministratorController::class, 'show']);
        Route::get('/user/{userId}', [AdministratorController::class, 'getByUserId']);
        //Route::post('/', [AdministratorController::class, 'store']);
        Route::put('/{id}', [AdministratorController::class, 'update']);
        Route::delete('/{id}', [AdministratorController::class, 'destroy']);
    });

    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################
    // ######################################################################################

    // [ API ADMIN PAGINATION]

    // [ CUSTOMERS ADMIN ]
    Route::prefix('admin/customers')->group(function () {
        Route::get('/', [CustomerAdminController::class, 'index']);
        Route::get('/{id}', [CustomerAdminController::class, 'show']);
        Route::get('/user/{user_id}', [CustomerAdminController::class, 'byUser']);
        Route::post('/', [CustomerAdminController::class, 'store']);
        Route::put('/{id}', [CustomerAdminController::class, 'update']);
        Route::patch('/{id}/verify', [CustomerAdminController::class, 'verify']);
        Route::patch('/{id}/add-credit', [CustomerAdminController::class, 'addCredit']);
        Route::patch('/{id}/deduct-credit', [CustomerAdminController::class, 'deductCredit']);
        Route::delete('/{id}', [CustomerAdminController::class, 'destroy']);
    });

    // [ DRIVER ADMIN ]
    Route::prefix('admin/drivers')->group(function () {
        Route::get('/', [DriverAdminController::class, 'index']);
        Route::get('/{id}', [DriverAdminController::class, 'show']);
        Route::post('/', [DriverAdminController::class, 'store']);
        Route::put('/{id}', [DriverAdminController::class, 'update']);
        Route::patch('{id}/verify', [DriverAdminController::class, 'verify']);
        Route::delete('/{id}', [DriverAdminController::class, 'destroy']);
    });

    // [ USER ]
    Route::prefix('users')->group(function () {
        Route::get('/', [UserController::class, 'index']);
        Route::get('/{id}', [UserController::class, 'show']);
        Route::post('/', [UserController::class, 'store']);
        Route::put('/{id}', [UserController::class, 'update']);
        Route::patch('{id}/verify', [UserController::class, 'verify']);
        Route::delete('/{id}', [UserController::class, 'destroy']);
    });

    // [ VEHICLES ADMIN ]
    Route::prefix('admin/vehicles')->group(function () {
        Route::get('/', [VehicleAdminController::class, 'index']);
        Route::get('/{id}', [VehicleAdminController::class, 'show']);
        Route::get('/driver/{driver_id}', [VehicleAdminController::class, 'byDriver']);
        Route::post('/', [VehicleAdminController::class, 'store']);
        Route::put('/{id}', [VehicleAdminController::class, 'update']);
        Route::patch('/{id}/verify', [VehicleAdminController::class, 'verify']);
        Route::patch('/{id}/reject', [VehicleAdminController::class, 'reject']);
        Route::delete('/{id}', [VehicleAdminController::class, 'destroy']);
    });

    // [ REFUNDS ]
    Route::prefix('admin/refunds')->group(function () {
        Route::get('/', [RefundController::class, 'index']);
        Route::get('/{type}/{id}', [RefundController::class, 'show']);
    });

});

