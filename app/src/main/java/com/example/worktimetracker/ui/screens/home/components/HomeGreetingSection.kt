package com.example.worktimetracker.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography

@Composable
fun HomeGreetingSection(
    modifier: Modifier = Modifier,
    state: SharedUiState = SharedUiState(),
    onAvatarClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Avatar(
                avatarUrl = state.user.avatarURL.toUri(),
                modifier = Modifier.size(48.dp),
                onClick = {
                    onAvatarClick()
                }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Hi, " + state.user.userFullName,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = AppTheme.colors.onSecondarySurface
                )
                Text(
                    text = "Vi tri " + state.user.department,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = AppTheme.colors.onSecondarySurface,
                    modifier = Modifier
                )
            }
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                tint = AppTheme.colors.onSecondarySurface,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}