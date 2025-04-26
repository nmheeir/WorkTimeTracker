package com.example.worktimetracker.ui.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgeDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.core.ext.format2
import com.example.worktimetracker.core.ext.parseDate
import com.example.worktimetracker.core.presentation.padding
import com.example.worktimetracker.core.presentation.util.hozPadding
import com.example.worktimetracker.data.remote.response.Task
import com.example.worktimetracker.ui.component.image.AvatarGroup
import com.example.worktimetracker.ui.theme.AppTheme
import kotlin.toString

@Composable
fun TaskCardItem(
    task: Task,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface,
            contentColor = AppTheme.colors.onRegularSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .hozPadding()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.padding.small)
        ) {
            Text(
                text = task.name,
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall)
            ) {
                Badge {
                    Text(
                        text = task.status.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Badge {
                    Text(
                        text = task.priority.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall)
            ) {
                AvatarGroup(
                    assignees = task.assignees,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null
                    )
                    Text(
                        text = task.dueDate.parseDate(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall)
                ) {
                    Icon(
                        imageVector = Icons.Default.Sms,
                        contentDescription = null
                    )
                    Text(
                        text = task.reports.size.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}