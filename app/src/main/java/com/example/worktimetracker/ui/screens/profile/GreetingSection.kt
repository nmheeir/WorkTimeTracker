package com.example.worktimetracker.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState
import com.example.worktimetracker.ui.screens.home.components.Avatar
import com.example.worktimetracker.ui.theme.Typography

@Composable
fun GreetingSection(
    modifier: Modifier = Modifier,
    state: SharedUiState,
    onCameraClick: () -> Unit = {}
) {
    val user = state.user
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box {
            Avatar(
                image = R.drawable.avatar,
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.Center)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        color = colorResource(id = R.color.white),
                        width = 1.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(colorResource(id = R.color.blue))
                    .padding(6.dp)
                    .clickable {
                        onCameraClick()
                    }
            )
        }
        Text(
            text = user.userName,
            style = Typography.titleLarge
        )
        Text(
            text = user.department,
            style = Typography.titleMedium,
            fontWeight = FontWeight.Normal
        )
    }
}