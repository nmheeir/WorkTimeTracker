package com.example.worktimetracker.ui.component.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.worktimetracker.ui.theme.AppTheme

@Composable
fun LinearBackground(
    content: @Composable () -> Unit
) {

    val startColor: Color = AppTheme.colors.backgroundStart // #1a365d
    val endColor: Color = AppTheme.colors.backgroundEnd // #1a365d
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        startColor,
                        endColor
                    )
                )
            )
    ) {
        content()
    }
}