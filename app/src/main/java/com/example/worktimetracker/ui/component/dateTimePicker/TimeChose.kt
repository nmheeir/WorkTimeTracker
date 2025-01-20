package com.example.worktimetracker.ui.component.dateTimePicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeChose(
    text: String,
    time: LocalTime?,
    event: () -> Unit
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val formattedTime = time?.format(timeFormatter) ?: "No Time"
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = { event() })
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clock_filled),
            contentDescription = null,
            tint = colorResource(R.color.blue),
            modifier = Modifier.
            padding(4.dp)
        )
        Column {
            Text(
                text = text,
                style = Typography.labelSmall,
                color = colorResource(id = R.color.text),
            )

            Text(
                text = formattedTime,  // Xử lý null cho time
                color = colorResource(id = R.color.colorTitle),
                style = Typography.labelSmall,
            )
        }
    }
}

@Preview
@Composable
fun TimeChosePreview() {
    TimeChose("From Day", LocalTime.now()) { Unit }
}