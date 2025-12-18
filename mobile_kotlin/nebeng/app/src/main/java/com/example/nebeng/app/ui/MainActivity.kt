package com.example.nebeng.app.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.nebeng.R
import com.example.nebeng.app.ui.navigation.CustomerNavGraph
import com.example.nebeng.app.ui.navigation.DriverNavGraph
import com.example.nebeng.databinding.ActivityMainBinding
import com.example.nebeng.feature_a_authentication.presentation.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val appRoleViewModel: AppRoleViewModel by viewModels()

    private var hasSetupGraph = false

    // 🔥 Halaman yang bottom nav harus disembunyikan
    private val hideBottomNavRoutes = setOf(
        "customer/nebeng_motor",
        "customer/nebeng_motor/ride_schedule",
        "customer/nebeng_motor/ride_schedule_detail",
        "customer/nebeng_motor/payment_method",
        "customer/nebeng_motor/payment_method_detail",
        "customer/nebeng_motor/payment_status",
        "customer/nebeng_motor/payment_success",
        "customer/nebeng_motor/on_the_way",
        "passenger_motor_map",
        "driver/nebeng_motor",
        "driver/nebeng_motor/on_the_way",
        "driver/nebeng_barang"
    )

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permission ->
            val granted = permission[Manifest.permission.ACCESS_FINE_LOCATION] == true
            Log.d("PERMISSION", "Location granted = $granted")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)

        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        // 🔥 Listener untuk menyembunyikan / menampilkan bottom nav
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.route in hideBottomNavRoutes) {
                binding.navView.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    appRoleViewModel.isLoggedInFlow,
                    appRoleViewModel.userTypeFlow
                ) {
                  isLoggedIn, userType -> isLoggedIn to userType
                }.collect { (isLoggedIn, userType) ->
                    if (!isLoggedIn) {
                        redirectToAuth()
                    } else {
                        setupGraphOnce(navController, userType)
                    }
                }
            }
        }
    }

    private fun setupGraphOnce(navController: NavController, role: String?) {
        if(hasSetupGraph) return
        hasSetupGraph = true

        when(role?.lowercase()) {
            "customer"  -> CustomerNavGraph.setup(navController)
            "driver"    -> DriverNavGraph.setup(navController)
            else        -> redirectToAuth()
        }
    }

    private fun redirectToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun setBottomNavVisibility(isVisible: Boolean) {
        binding.navView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}