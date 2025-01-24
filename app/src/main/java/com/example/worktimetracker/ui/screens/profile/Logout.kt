package com.example.worktimetracker.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.lOptionProfile

@Composable
fun Logout(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val logoutOption = lOptionProfile.last()
    Row(
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Red.copy(alpha = 0.1f))
            .padding(8.dp)
            .clickable {
                onClick()
            }
    ) {
        Icon(
            painter = painterResource(id = logoutOption.icon),
            contentDescription = logoutOption.title,
            tint = colorResource(id = R.color.red),
            modifier = Modifier
                .clip(CircleShape)
                .background(color = colorResource(id = R.color.red).copy(alpha = 0.3f))
                .padding(8.dp)
        )
        Text(
            text = logoutOption.title,
            style = Typography.labelLarge,
            color = colorResource(id = R.color.red),
            modifier = Modifier.weight(1f)
        )
    }
}