package com.example.worktimetracker.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState
import com.example.worktimetracker.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun HomeGreetingSection(
    modifier: Modifier = Modifier,
    state: SharedUiState = SharedUiState()
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = colorResource(id = R.color.blue))
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // TODO: Đổi thành asyncImage
            Avatar(image = R.drawable.avatar, modifier = Modifier.size(32.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Hi, " + state.user.userFullName,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = "Vi tri " + state.user.department,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier
                )
            }
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                tint = colorResource(id = R.color.white)
            )
        }
    }
}