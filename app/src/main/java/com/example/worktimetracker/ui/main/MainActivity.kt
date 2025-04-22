package com.example.worktimetracker.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.worktimetracker.core.presentation.util.DeviceTokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.set
import com.example.worktimetracker.ui.component.background.LinearBackground
import com.example.worktimetracker.ui.main.component.ConnectivityStatus
import com.example.worktimetracker.ui.navigation.navigationBuilder
import com.example.worktimetracker.ui.theme.WorkTimeTrackerTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

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

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            val context = this.applicationContext
            context.dataStore.set(DeviceTokenKey, it.result)
            Timber.d("FirebaseMessaging Token: ${it.result}")
        }

        // Configure osmdroid cho openstreetmap
        Configuration.getInstance().load(this, this.getPreferences(MODE_PRIVATE))
        setContent {
            WorkTimeTrackerTheme {
                Box {
                    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

                    val navController = rememberNavController()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .safeContentPadding()
                    ) {
                        ConnectivityStatus()
                        LinearBackground {
                            NavHost(
                                startDestination = startDestination,
                                navController = navController,
                                enterTransition = {
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Left
                                    )
                                },
                                exitTransition = {
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Left
                                    )
                                },
                                popEnterTransition = {
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Right
                                    )
                                },
                                popExitTransition = {
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Right
                                    )
                                }
                            ) {
                                navigationBuilder(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

