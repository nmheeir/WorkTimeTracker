package com.example.worktimetracker.core.presentation.util

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Stable
@Composable
fun ColumnScope.Gap(height: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(height))
}

@Stable
@Composable
fun RowScope.Gap(width: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(width))
}

@Stable
@Composable
fun LazyItemScope.Gap(height: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(height))
}