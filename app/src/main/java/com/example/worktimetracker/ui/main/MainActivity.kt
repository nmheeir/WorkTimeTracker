package com.example.worktimetracker.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.worktimetracker.ui.main.component.ConnectivityStatus
import com.example.worktimetracker.ui.navigation.NavGraph
import com.example.worktimetracker.ui.theme.WorkTimeTrackerTheme
import com.example.worktimetracker.ui.util.BiometricPromptManager
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val promptManager: BiometricPromptManager by lazy {
        BiometricPromptManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition.value
            }
        }

        // Configure osmdroid cho openstreetmap
        Configuration.getInstance().load(this, this.getPreferences(MODE_PRIVATE))
        // tạo fused lấy vị trí hiện tại
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            val navController = rememberNavController()
            WorkTimeTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .safeDrawingPadding()
                            .fillMaxSize()
                    ) {
                        Column (
                            modifier = Modifier.fillMaxSize()
                        ) {
                            ConnectivityStatus()
                            NavGraph(
                                sDestination = viewModel.startDestination.value,
                                navController = navController,
                                promptManager = promptManager
                            )
                        }
                    }
                }
            }
        }
    }
}

