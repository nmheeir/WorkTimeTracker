package com.example.worktimetracker.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun WorkTimeTrackerTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppColors provides extendedColors
    ) {
        MaterialTheme(
            typography = Typography,
            content = content,
        )
    }
}

object AppTheme {
    val colors: AppColors
            @Composable
            get() = LocalAppColors.current
}