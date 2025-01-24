package com.example.worktimetracker.ui.screens.auth.session

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.worktimetracker.R
import com.example.worktimetracker.data.local.db.entity.UserSession
import com.example.worktimetracker.ui.screens.home.components.Avatar
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography


@Composable
fun SessionItem(
    modifier: Modifier = Modifier,
    user: UserSession,
    onClick: () -> Unit
) {
    OutlinedCard(
        border = BorderStroke(1.dp, AppTheme.colors.blurredText),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                Avatar(
                    avatarUrl = user.avatarUrl.toUri(),
                    modifier = Modifier
                        .size(64.dp),
                )
                Text(
                    text = user.username,
                    style = Typography.titleLarge.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
            )
        }
    }
}

