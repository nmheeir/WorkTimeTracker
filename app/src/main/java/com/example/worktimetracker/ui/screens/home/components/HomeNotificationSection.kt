package com.example.worktimetracker.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography


@Preview(showBackground = true)
@Composable
fun NotificationCard(
    modifier: Modifier = Modifier,
    notification: HomeNotification = listHomeNotification[0]
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = notification.title,
                    style = Typography.titleLarge,
                    color = colorResource(id = R.color.blue)
                )
                Text(
                    text = notification.desc,
                    style = Typography.titleMedium,
                    color = colorResource(id = R.color.black)
                )
                Text(
                    text = "Date: " + notification.date,
                    style = Typography.titleSmall,
                    color = colorResource(id = R.color.black).copy(alpha = 0.3f)
                )
            }
        }
    }
}

data class HomeNotification(
    val title: String,
    val desc: String,
    val date: String
)

val listHomeNotification = listOf(
    HomeNotification(
        title = "Payroll Notification",
        desc = "Payroll Notification Description",
        date = "2023-01-01"
    ),
    HomeNotification(
        title = "Payroll Notification",
        desc = "Payroll Notification Description",
        date = "2023-01-01"
    ),
    HomeNotification(
        title = "Payroll Notification",
        desc = "Payroll Notification Description",
        date = "2023-01-01"
    )
)