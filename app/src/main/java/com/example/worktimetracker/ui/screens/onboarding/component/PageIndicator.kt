package com.example.worktimetracker.ui.screens.onboarding.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = colorResource(id = R.color.blue),
    unselectedColor: Color = colorResource(id = R.color.light_gray)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pageSize) { page ->
            val width by animateDpAsState(
                targetValue = if (page == selectedPage) 48.dp else 16.dp,
                label = ""
            )
            Divider(
                modifier = Modifier
                    .width(width),
                thickness = 3.dp,
                color = if (page == selectedPage) selectedColor else unselectedColor
            )
        }
    }
}

@Composable
fun CirclePageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = colorResource(id = R.color.blue),
    unselectedColor: Color = colorResource(id = R.color.light_gray)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pageSize) { page ->
            val width by animateDpAsState(
                targetValue = if (page == selectedPage) 48.dp else 16.dp,
                label = ""
            )
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
                    .size(16.dp)
            )
        }
    }

}