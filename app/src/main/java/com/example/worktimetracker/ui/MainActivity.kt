package com.example.worktimetracker.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.worktimetracker.ui.navigation.NavGraph
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.theme.WorkTimeTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )
        setContent {
        val navController = rememberNavController()
            WorkTimeTrackerTheme {
                Box(
                    modifier = Modifier
                        .safeDrawingPadding()
                ) {
                    NavGraph(
                        route = Route.HomeScreen.route,
                        navController = navController
                    )
                }
            }
        }
    }
}