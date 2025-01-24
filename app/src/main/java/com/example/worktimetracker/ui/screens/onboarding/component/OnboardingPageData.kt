package com.example.worktimetracker.ui.screens.onboarding.component

import androidx.annotation.DrawableRes
import com.example.worktimetracker.R

data class OnBoardingPageData(
    val title: String,
    val desc: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    OnBoardingPageData(
        title = "Easily track attendance",
        desc = "Manage work hours efficiently with seamless attendance tracking right in the app.",
        image = R.drawable.onboarding1
    ),
    OnBoardingPageData(
        title = "Streamline disciplinary management",
        desc = "Monitor and enforce internal policies to ensure transparency and discipline.",
        image = R.drawable.onboarding2
    ),
    OnBoardingPageData(
        title = "Optimize HR operations",
        desc = "Leverage integrated HR tools to simplify and enhance your workforce management.",
        image = R.drawable.onboarding3
    )
)
