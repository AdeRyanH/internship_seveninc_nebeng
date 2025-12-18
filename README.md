# 📱 MOBILE APPLICATION – NEBENG

### 🧰 TECH STACK

> (Dapat dilihat langsung pada file `build.gradle.kts` di domain `app`)

1. Retrofit
2. Dependency Injection (Dagger)
3. Jetpack Compose
4. Room
5. Preferences DataStore
6. OpenStreetMap
7. GPS
8. Mockito (Unit Testing)
9. Material3
10. Coroutines
11. Logging Interceptor
12. Swipe Refresh (UI swipe untuk refresh page)
13. Jetpack Compose Preview Tooling
14. Midtrans (via backend)

---

### 🏗️ ARCHITECTURE

1. Clean Architecture (MVI mirip MVVM)
2. Single Responsibility of Concern
3. Backend hanya CRUD biasa & mobile **tidak mengusik customization source code backend**
4. Seluruh manipulasi data berada di file `*Interactor.kt` atau `*Aggregator.kt` dengan bantuan directory `session` pada layer `domain` di setiap directory `feature_a_*`, yang dihubungkan dengan raw data hasil mapper pada layer domain di directory `model`
5. Alur pengiriman data:

#### A. Lokasi di `feature_*`

\*Api.kt
→ *Repository.kt + *RepositoryImpl.kt
→ ~/feature\**/domain/model/*Summary.kt
→ ~/feature\*_/data/remote/mapper/_.kt
→ LANJUT\*KE_WILAYAH feature_a\*

**Note:**  
Perlu dependency injection di: ~/nebeng/app/src/main/java/com/example/nebeng/feature\_*/di/*Module.kt

#### B. Lokasi di `feature_a_*`

`~/feature_a_*/domain/mapper/.kt`
→ `~/feature_a_*/domain/model/`
→ `~/feature_a_*/domain/usecase/*UseCase.kt`
→ `~/feature_a_*/domain/usecase/*UseCases.kt`
→ `DEPENDENCY_INJECTION_AKHIR`
→ `~/feature_a_*/domain/aggregator/*Aggregator.kt`
atau
`~/feature_a_*/domain/interactor/*Interactor.kt`
→ `~/feature_a_*/presentation/*ViewModel.kt`

**Note:**

- Seluruh akses `~/feature_a_*/presentation/*ViewModel.kt` dilakukan melalui: `~/nebeng/app/src/main/java/com/example/nebeng/feature_a_*/presentation/navigation/*.kt`
- Seluruh `*Screen.kt` **murni hanya mengakses variable data class**: `~/nebeng/app/src/main/java/com/example/nebeng/feature_a_*/domain/model/` karena directory ini berisi **final raw data 1:1 seperti tabel backend**, dengan bantuan `~/nebeng/app/src/main/java/com/example/nebeng/feature_a_*/domain/session/` atau customization lainnya.

---

### 🚀 FITUR (BERDASARKAN ROLE AKUN)

#### A. Customer

- ✅ Nebeng Motor Customer  
  _(Blueprint fondasi dari seluruh fitur di menu Homepage bagi role customer & driver — kecuali tahap fitur rating)_
- ☑️ Nebeng Mobil
- ☑️ Nebeng Barang
- ☑️ Nebeng Barang Transportasi Umum
- ☑️ History
- ☑️ Chat
- ☑️ Profile

#### B. Driver

- ✅ Nebeng Motor  
  _(Khusus di bagian realtime GPS untuk mengirim current location)_
- ☑️ Nebeng Mobil
- ☑️ Nebeng Barang
- ☑️ Nebeng Barang Transportasi Umum
- ☑️ History
- ☑️ Chat
- ☑️ Profile

#### C. Terminal

- Masih belum dibuat

---

### 🧪 UNIT TESTING (On-Going)

#### 1. Main Foundation Application

- ☑️ app

#### 2. Core System

- ☑️ core

#### 3. User Interface Utama

- ☑️ feature_a_authentication
- ☑️ feature_a_chat
- ☑️ feature_a_history_order
- ☑️ feature_a_homepage

#### 4. API Data Retrieval (On-Going)

- ✅ feature_credit_score_log
- ✅ feature_customer
- ✅ feature_driver
- ☑️ feature_driver_commission
- ☑️ feature_driver_location_good
- ☑️ feature_driver_location_ride
- ✅ feature_driver_withdrawal
- ☑️ feature_finance (sepertinya di sisi mobile tidak perlu)
- ✅ feature_goods_ride
- ☑️ feature_goods_ride_booking
- ✅ feature_goods_transaction
- ☑️ feature_notification
- ☑️ feature_passenger_pricing
- ✅ feature_passenger_ride
- ✅ feature_passenger_transaction
- ✅ feature_passenger_ride_booking
- ✅ feature_payment_method
- ☑️ feature_pricing
- ✅ feature_rating
- ☑️ feature_terminal
- ☑️ feature_user
- ✅ feature_vehicle

---

### ⚠️ NOTE IMPORTANT

1. **Base URL wajib diubah** di: `~/mobile_kotlin/nebeng/app/src/main/java/com/example/nebeng/core/network/ApiClient.kt` dan perlu menambah/mengubah `~/mobile_kotlin/nebeng/app/src/main/res/xml/network_security_config.xml`

2. **Jangan pernah meletakkan ViewModel di file `*Screen.kt`**, karena akan menyebabkan **tidak pernah bisa menampilkan preview UI Jetpack Compose**

3. **Wajib memakai BASE URL pada IP Address yang sama**, karena Mobile App berbeda teknisnya dengan localhost backend pada web dev.

   - Laptop & mobile device harus terhubung pada WiFi yang sama (non-public seperti Indihome)
   - Jika WiFi public (seprti Wifi.id), mobile device harus hotspot dan laptop terhubung ke hotspot tersebut

   **Cara cek IP:**

   - Linux (Arch Linux):
     ```
     ip addr show
     ```
   - Android (Termux):
     ```
     ifconfig
     ```

   **Contoh:**

   - Laptop IP: `192.168.123.50/24`
   - Android IP: `192.168.123.80/24`

   Maka BASE URL backend: `http://192.168.123.50:8000/`

   Note: Masih belum begitu paham soal Network, jadi perlu dicari tahu lebih lanjut selain dari acuan dokumentasi ini

4. **Seluruh akses permission wajib ditambahkan di `AndroidManifest.xml`**
5. **Perlu mencari sumber informasi acuan tambahan jika pakai API yang terdeploy karena sejauh ini hanya diletakkan di build.gradle.kts domain app atau mungkin diletakkan di file .impl lalu menambahkannya ke file .env**

---

### 🔗 Link / Source Reference

1. Android Developer Website
2. https://kotlinlang.org/docs/home.html
3. Dicoding website
4. https://youtube.com/shorts/SAD8flVdILY?si=esOqEaZmF_Rq4Abk
5. Stackoverflow
6. Hackerrank website
7. https://medium.com/design-bootcamp/modern-android-app-architecture-with-clean-code-principles-2025-edition-95f4c2afeadb
8. https://proandroiddev.com/clean-architecture-for-android-mvi-1763ab78855e
9. https://youtu.be/EF33KmyprEQ?si=JBREKQLkNRL2jhw4
10. https://medium.com/@sharmapraveen91/how-to-define-a-clean-architecture-structure-for-a-large-android-app-a705b7d40cac
11. https://www.openstreetmap.org/
12. https://wiki.openstreetmap.org/wiki/Android
13. https://github.com/osmdroid/osmdroid
14. https://wiki.openstreetmap.org/wiki/Using_OpenStreetMap
15. https://wiki.openstreetmap.org/wiki/Main_Page
16. https://youtu.be/JYUw7He0SE0?si=QGNI77VlzGZz3454
17. https://youtu.be/rQocMYKpOWk?si=eIP-8ZjZpjY6hUta

---

---

---

# 📱 BACKEND API – NEBENG

### 🧰 TECH STACK

> (lihat juga file `.env.example`)

1. midtrans/midtrans-php
2. PhpMyAdmin
3. Laravel 12
4. PHP
5. laravel/sanctum
6. tymon/jwt-auth

---

### 🏗️ ARCHITECTURE

1. Default
2. Seluruh bussiness logic ada di `~/backend_api/app/Http/Services/*.php`
3. Backend untuk hanya CRUD biasa sesuai tabel database dari tempat magang & mobile **tidak mengusik customization source code backend**. Untuk versi website terdapat beberapa customization dari source code backend yang digunakan oleh mobile android app.
4. Terdapat 3 command yaitu `php artisan serve --host=IP_ADDRESS_LAPTOP_ANDA --port=8000` untuk android app, `php artisan serve` untuk website, dan `php artisan schedule:work` untuk membaca status settlement dari website midtrans.
5. Customization ada di directory `~/backend_api/routes/console.php`, `~/backend_api/config/*.php`, dan `~/backend_api/Console/Command/*.php`. Note bisa dimodifikasi pada directory lain jika punya pengetahuan yang lebih luas.

---

### 🚀 Endpoint

#### A. Murni CRUD 1:1 ke tabel database

> (Mobile app android kotlin menggunakan ini tanpa mengusik perubahan CRUD sedikitpun kecuali passenger_transactions dan goods_transactions untuk kebutuhan midtrans)
> Endpoint dapat dilihat di `~/backend_api/routes/api.php`

- ✅ credit_score_logs
- ✅ customers
- ✅ districts
- ✅ driver_commissions
- ✅ drivers
- ✅ driver_location_goods
- ✅ driver_location_rides
- ✅ driver_withdrawals
- ✅ goods_ride_bookings
- ✅ goods_rides
- ✅ goods_transactions
- ✅ passenger_pricings
- ✅ passenger_ride_bookings
- ✅ passenger_rides
- ✅ passenger_transactions
- ✅ provinces
- ✅ ratings
- ✅ regencies
- ✅ terminals
- ✅ users
- ✅ vehicles

#### B. Customizations untuk kebutuhan Website

> (Ada customization dari database murni)

- ✅
- ✅
- ✅

---

### ⚠️ NOTE IMPORTANT

1. **Copy-Paste file .env.example** dan ubah isinya berdasarkan kebutuhan terutama di line source code untuk midtrans dan web dev

2. **Jangan pernah upload file .env**, karena isi dari file `.env` adalah informasi credential. Cara untuk tidak upload adalah dengan menambahkannya pada file `.gitignore`

3. **Wajib memakai BASE URL pada IP Address yang sama**, karena Mobile App berbeda teknisnya dengan localhost backend pada web dev.

   - Laptop & mobile device harus terhubung pada WiFi yang sama (non-public seperti Indihome)
   - Jika WiFi public (seprti Wifi.id), mobile device harus hotspot dan laptop terhubung ke hotspot tersebut

   **Cara cek IP:**

   - Linux (Arch Linux):
     ```
     ip addr show
     ```
   - Android (Termux):
     ```
     ifconfig
     ```

   **Contoh:**

   - Laptop IP: `192.168.123.50/24`
   - Android IP: `192.168.123.80/24`

   Maka BASE URL backend: `http://192.168.123.50:8000/`

   Note: Masih belum begitu paham soal Network, jadi perlu dicari tahu lebih lanjut selain dari acuan dokumentasi ini

4. **Seluruh logic/security/arsitektur** di-customization berdasarkan pengetahuan best practices.
5. **Seluruh endpoint berdasarkan file `~/backend_api/routes/api.php` dan isi Body-Request berdasarkan file `~/backend_api/app/Http/Services/*php`** lalu bisa testing endpoint seperti menggunakan Postman atau yang lainnya. Note: Seluruh endpoint perlu token semua kecuali saat login, signup, dan logout.

---

### 🔗 Link / Source Reference

1. https://laravel.com/docs/12.x/installation
2. https://youtu.be/T1TR-RGf2Pw?si=phlGKqG1G-qf7_Qe
3. https://youtu.be/tdDARiQOmZE?si=HxaPuFn0YSNYPDSN
4. https://share.google/2j7Qk4oWqrYBLv45i
5. https://docs.midtrans.com/reference/quick-start-1
6. https://share.google/8VCgud1nhQgjVNOFZ
