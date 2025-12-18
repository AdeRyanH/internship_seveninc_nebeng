plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.nebeng"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.nebeng"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {
    implementation(libs.androidx.activity)
    val room_version = "2.8.1"
    val composeBom = platform("androidx.compose:compose-bom:2025.09.01")
//    val nav_version = "2.9.5"
    val nav_version = "2.9.5"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // [NEBENG]
    //noinspection GradleDependency,UseTomlInstead
    implementation("androidx.room:room-runtime:${room_version}")
    ksp(libs.androidx.room.compiler)
    //dependency injection kotlin android native non multiplatform
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // Compose
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.material3)  // material 3 design
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.foundation) // versi terbaru
    implementation(libs.coil.compose)
    implementation(libs.material3)
    implementation(libs.androidx.material3.window.size.class1)
    implementation(libs.androidx.material3.adaptive.navigation.suite)

    // Android Studio Preview support
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // Preferences DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Retrofit
    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    
    // Mockito
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test.v152) // For testing suspending functions

    // Swipe Refresh
    // build.gradle (module)
    implementation(libs.accompanist.swiperefresh)
//    implementation(libs.androidx.material.pull.refresh)
//    implementation(libs.androidx.material3.pullrefresh)

    // MAPLIBRE (OpenStreetMap)
    implementation(libs.android.sdk)
    implementation(libs.android.plugin.annotation.v9)
    implementation(libs.android.sdk.v1100)
    implementation(libs.android.sdk.v1020)

    implementation(libs.osmdroid.android)

    // GPS
    implementation(libs.play.services.location)
}