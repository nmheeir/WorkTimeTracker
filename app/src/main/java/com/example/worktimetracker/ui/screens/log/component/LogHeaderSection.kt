package com.example.worktimetracker.ui.screens.log.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun LogHeaderSection(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Logs",
            style = Typography.labelLarge
        )
        Row {
            // TODO: Thêm hàm onClick
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_square),
                    contentDescription = null
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = null
                )
            }
        }
    }
}