package com.example.worktimetracker.core.presentation.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.example.worktimetracker.core.presentation.padding

fun Modifier.hozPadding() =
    this.padding(horizontal = MaterialTheme.padding.mediumSmall)