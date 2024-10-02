package com.example.worktimetracker.ui.screens.activity.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateChose(
    text: String,
    time: LocalDate?,
    event: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = { event() })
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
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
                text = time?.toString() ?: "No Date",  // Xử lý null cho time
                color = colorResource(id = R.color.colorTitle),
                style = Typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
fun DateChosePreview() {
    DateChose("From Day", LocalDate.now()) { Unit }
}