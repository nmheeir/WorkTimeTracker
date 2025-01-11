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
        title = "Easy way to confirm you attendance",
        desc = "It a long established face that a reader will be distracted by the readable content.",
        image = R.drawable.onboarding1
    ),
    OnBoardingPageData(
        title = "Disciplinary in your hand",
        desc = "It a long established face that a reader will be distracted by the readable content.",
        image = R.drawable.avatar
    ),
    OnBoardingPageData(
        title = "Lorem Ipsum is simply dummy",
        desc = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        image = R.drawable.avatar
    )
)