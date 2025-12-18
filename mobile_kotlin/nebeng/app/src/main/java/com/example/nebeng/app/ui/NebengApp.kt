package com.example.nebeng.app.ui

import android.app.Application
import android.preference.PreferenceManager
import com.example.nebeng.core.session.data.UserPreferencesRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import javax.inject.Inject

@HiltAndroidApp
class NebengApp : Application() {
    @Inject
    lateinit var userPrefsRepo: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        instance = this

        /* ===============================
         * OSMDROID GLOBAL CONFIG (WAJIB)
         * =============================== */
        val ctx                 = applicationContext
        val config              = Configuration.getInstance()
        config.userAgentValue   = ctx.packageName
        config.load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        // Preload DataStore secara sinkron ke RoleCache
        CoroutineScope(Dispatchers.IO).launch {
            val cachedRole          = userPrefsRepo.userTypeFlow.first()
            val isLoggedIn          = userPrefsRepo.isLoggedInFlow.first()
            RoleCache.role          = cachedRole
            RoleCache.isLoggedIn    = isLoggedIn
        }
    }

    companion object {
        lateinit var instance: NebengApp
            private set

        val appContext get() = instance.applicationContext
    }
}

object RoleCache {
    var role: String? = null
    var isLoggedIn: Boolean = false
}
