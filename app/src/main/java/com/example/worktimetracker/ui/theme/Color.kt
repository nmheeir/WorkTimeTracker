package com.example.worktimetracker.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


@Immutable
data class AppColors (
    val backgroundStart: Color,
    val backgroundEnd: Color,
    val onBackground: Color,
    val darkerSurface : Color,
    val onDarkerSurface : Color,
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
        darkerSurface = Color.Unspecified,
        onDarkerSurface = Color.Unspecified,
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
    backgroundStart = Color(0xFF5893e3),
    backgroundEnd = Color(0xFF3e54e6),
    darkerSurface = Color.Black.copy(alpha = 0.1f),
    onDarkerSurface = Color.White,
    onBackground = Color.White,
    secondarySurface = Color.White.copy(alpha = 0.5f),
    onSecondarySurface = Color.White,
    regularSurface = Color.White.copy(alpha = 0.15f),
    onRegularSurface = Color.White,
    actionSurface = Color(0xFF5080C9),
    onActionSurface = Color.White,
    hightlightSurface = Color(0xFF5080C9),
    onHighlightSurface = Color.White,

    focusedBorderTextField = Color.White,
    unfocusedBorderTextField = Color.White.copy(alpha = 0.5f),
    blurredText = Color.White.copy(alpha = 0.7f),
    onBackgroundBlue = Color(0xFF3085FE)



)