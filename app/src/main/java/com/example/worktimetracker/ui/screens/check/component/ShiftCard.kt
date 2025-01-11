package com.example.worktimetracker.ui.screens.check.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.helper.ISOFormater
import com.example.worktimetracker.ui.theme.AppTheme
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.AddressBook
import compose.icons.fontawesomeicons.solid.Clock
import compose.icons.fontawesomeicons.solid.HourglassEnd
import compose.icons.fontawesomeicons.solid.HourglassStart
import compose.icons.fontawesomeicons.solid.UserClock

@Composable
fun ShiftCard(shift: Shift, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }, // Gọi lambda ở đây
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ISOFormater.formatDateTimeToDate1(shift.start),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.UserClock,
                        contentDescription = "Shift",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    ShiftCardItem(
                        title = stringResource(R.string.start_time),
                        text = ISOFormater.formatDateTimeToTime(shift.start),
                        icon = FontAwesomeIcons.Solid.HourglassStart
                    )
                    ShiftCardItem(
                        title = stringResource(R.string.check_in),
                        text = ISOFormater.formatDateTimeToTime(shift.checkIn),
                        icon = FontAwesomeIcons.Solid.UserClock
                    )
                }
                Column {
                    ShiftCardItem(
                        title = stringResource(R.string.end_time),
                        text = ISOFormater.formatDateTimeToTime(shift.end),
                        icon = FontAwesomeIcons.Solid.HourglassEnd
                    )
                    ShiftCardItem(
                        title = stringResource(R.string.check_out),
                        text = ISOFormater.formatDateTimeToTime(shift.checkOut),
                        icon = FontAwesomeIcons.Solid.UserClock
                    )
                }
                Column {
                    ShiftCardItem(
                        title = stringResource(R.string.duration),
                        text = shift.workDuration.toString() + " hours",
                        icon = FontAwesomeIcons.Solid.Clock
                    )
                    ShiftCardItem(
                        title = stringResource(R.string.shift_type),
                        text = shift.shiftTypeEnum.toString(),
                        icon = FontAwesomeIcons.Solid.AddressBook
                    )
                }
            }
        }
    }
}

@Composable
fun ShiftCardItem(
    title: String,
    text: String,
    icon: ImageVector
) {
    Column (
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}
