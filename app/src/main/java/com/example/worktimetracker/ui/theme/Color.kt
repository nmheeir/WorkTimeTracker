package com.example.worktimetracker.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


@Immutable
data class AppColors (
    val backgroundStart: Color,
    val backgroundEnd: Color,
    val onBackground: Color,
    val surface : Color,
    val onSurface : Color,
    val secondarySurface: Color,
    val onSecondarySurface: Color,
    val regularSurface : Color,
    val onRegularSurface: Color,
    val actionSurface: Color,
    val onActionSurface: Color,
    val hightlightSurface: Color,
    val onHighlightSurface: Color,

    val focusedBorderTextField : Color,
    val unfocusedBorderTextField : Color,
    val blurredText: Color,

    val onBackgroundBlue: Color
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        backgroundStart = Color.Unspecified,
        backgroundEnd = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        onBackground = Color.White,
        secondarySurface = Color.Unspecified,
        onSecondarySurface = Color.Unspecified,
        regularSurface = Color.Unspecified,
        onRegularSurface = Color.Unspecified,
        actionSurface = Color.Unspecified,
        onActionSurface = Color.Unspecified,
        hightlightSurface = Color.Unspecified,
        onHighlightSurface = Color.Unspecified,


        focusedBorderTextField = Color.Unspecified,
        unfocusedBorderTextField = Color.Unspecified,
        blurredText = Color.Unspecified,
        onBackgroundBlue = Color.Unspecified
    )
}

val extendedColors = AppColors(
    backgroundStart = Color(0xFF1A365D),
    backgroundEnd = Color(0xFF0EA5E9),
    surface = Color.White.copy(alpha = 0.5f),
    onSurface = Color.White,
    onBackground = Color.White,
    secondarySurface = Color.Unspecified,
    onSecondarySurface = Color.Unspecified,
    regularSurface = Color.Unspecified,
    onRegularSurface = Color.Unspecified,
    actionSurface = Color.Blue,
    onActionSurface = Color.White,
    hightlightSurface = Color.Unspecified,
    onHighlightSurface = Color.Unspecified,

    focusedBorderTextField = Color.White,
    unfocusedBorderTextField = Color.White.copy(alpha = 0.5f),
    blurredText = Color(0xFF8f8c8c),
    onBackgroundBlue = Color(0xFF3085FE)

)