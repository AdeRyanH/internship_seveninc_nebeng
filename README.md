# 📱 MOBILE APPLICATION – NEBENG

## 🧰 TECH STACK

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

## 🏗️ ARCHITECTURE

1. Clean Architecture (MVI mirip MVVM)
2. Single Responsibility of Concern
3. Backend hanya CRUD biasa & mobile **tidak mengusik customization source code backend**
4. Seluruh manipulasi data berada di file `*Interactor.kt` atau `*Aggregator.kt` dengan bantuan directory `session` pada layer `domain` di setiap directory `feature_a_*`, yang dihubungkan dengan raw data hasil mapper pada layer domain di directory `model`
5. Alur pengiriman data:

### A. Lokasi di `feature_*`

\*Api.kt
→ Repository.kt
→ Repository.kt + RepositoryImpl.kt
→ ~/feature*/domain/model/Summary.kt
→ ~/feature*/data/remote/mapper/.kt
→ LANJUT*KE_WILAYAH feature_a*

**Note:**  
Perlu dependency injection di: ~/nebeng/app/src/main/java/com/example/nebeng/feature\_*/di/*Module.kt

---

### B. Lokasi di `feature_a_*`

~/feature*a*/domain/mapper/.kt
→ ~/feature*a*/domain/model/
→ ~/feature*a\*\*/domain/usecase/UseCase.kt
→ ~/feature*a*/domain/usecase/UseCases.kt
→ DEPENDENCY*INJECTION_AKHIR
→ ~/feature_a*/domain/aggregator/Aggregator.kt
atau
~/feature*a*/domain/interactor/Interactor.kt
→ ~/feature*a*/presentation/*ViewModel.kt

**Note:**

- Seluruh akses `~/feature_a_*/presentation/*ViewModel.kt` dilakukan melalui: ~/nebeng/app/src/main/java/com/example/nebeng/feature_a_homepage/presentation/navigation/\*.kt
- Seluruh `*Screen.kt` **murni hanya mengakses variable data class**: ~/nebeng/app/src/main/java/com/example/nebeng/feature*a*/domain/model/ karena directory ini berisi **final raw data 1:1 seperti tabel backend**, dengan bantuan ~/nebeng/app/src/main/java/com/example/nebeng/feature*a*/domain/session/ atau customization lainnya.

---

## 🚀 FITUR

### A. Customer

- ✅ Nebeng Motor Customer  
  _(Blueprint fondasi dari seluruh fitur di menu Homepage bagi role customer & driver — kecuali tahap fitur rating)_
- ☑️ Nebeng Mobil
- ☑️ Nebeng Barang
- ☑️ Nebeng Barang Transportasi Umum
- ☑️ History
- ☑️ Chat
- ☑️ Profile

---

### B. Driver

- ✅ Nebeng Motor  
  _(Khusus di bagian realtime GPS untuk mengirim current location)_
- ☑️ Nebeng Mobil
- ☑️ Nebeng Barang
- ☑️ Nebeng Barang Transportasi Umum
- ☑️ History
- ☑️ Chat
- ☑️ Profile

---

### C. Terminal

- Masih belum dibuat

---

## 🧪 UNIT TESTING (On-Going)

### 1. Main Foundation Application

- ☑️ app

### 2. Core System

- ☑️ core

### 3. User Interface Utama

- ☑️ feature_a_authentication
- ☑️ feature_a_chat
- ☑️ feature_a_history_order
- ☑️ feature_a_homepage

### 4. API Data Retrieval

- ✅ feature_credit_score_log
- ✅ feature_customer
- ✅ feature_driver
- ☑️ feature_driver_commission
- ☑️ feature_driver_location_good
- ☑️ feature_driver_location_ride
- ✅ feature_driver_withdrawal
- ☑️ feature*finance *(sepertinya di sisi mobile tidak perlu)\_
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

## ⚠️ NOTE IMPORTANT

1. **Base URL wajib diubah** di: ~/mobile_kotlin/nebeng/app/src/main/java/com/example/nebeng/core/network/ApiClient.kt dan perlu menambah/mengubah ~/mobile_kotlin/nebeng/app/src/main/res/xml/network_security_config.xml

2. **Jangan pernah meletakkan ViewModel di file `*Screen.kt`**, karena akan menyebabkan **tidak pernah bisa menampilkan preview UI Jetpack Compose**

3. **Wajib memakai BASE URL pada IP Address yang sama**, karena Mobile App berbeda teknisnya dengan localhost backend pada web dev.

   - Laptop & mobile device harus terhubung pada WiFi yang sama (non-public seperti Indihome)
   - Jika WiFi public (Wifi.id), mobile device harus hotspot dan laptop terhubung ke hotspot tersebut

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

4. **Seluruh akses permission wajib ditambahkan di `AndroidManifest.xml`**
