package com.example.nebeng.feature_a_homepage.presentation.fragment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nebeng.app.ui.MainActivity
import com.example.nebeng.app.ui.common.RoleAwareFragment
import com.example.nebeng.feature_a_homepage.presentation.HomepageViewModel
import com.example.nebeng.feature_a_homepage.presentation.navigation.HomepageNavHost
import com.example.nebeng.feature_a_homepage.presentation.screen_role.driver.HomepageDriverScreenUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomepageFragment : RoleAwareFragment() {

    private val viewModel: HomepageViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    override fun CustomerUI() {
        HomepageNavHost(
            userType = "customer",
            viewModel = viewModel,
            onRouteChanged = { route ->
//                val hide = route.startsWith("nebeng_motor") || route == "passenger_motor_map"
                val hide = route.startsWith("customer/nebeng_motor") || route == "passenger_motor_map"
                setBottomNavVisible(!hide)
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    override fun DriverUI() {
//        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//        HomepageDriverScreenUi(uiState)
        HomepageNavHost(
            userType = "driver",
            viewModel = viewModel,
            onRouteChanged = { route ->
//                val hide = route.startsWith("driver/nebeng_motor") || route == "driver/nebeng_motor/on_the_way"
                val hide = route.startsWith("driver/nebeng_motor/on_the_way") || route == "driver/nebeng_motor/on_the_way"
                setBottomNavVisible(hide)
            }
        )
    }

    fun setBottomNavVisible(visible: Boolean) {
        val activity = requireActivity() as MainActivity
        activity.setBottomNavVisibility(visible)
    }
}
