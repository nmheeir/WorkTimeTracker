package com.example.worktimetracker.ui.screens.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography


@Composable
fun MyProfileItem(
    modifier: Modifier = Modifier,
    item: MyProfileItem
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = item.title,
            style = Typography.bodySmall,
            color = AppTheme.colors.blurredText
        )
        Text(
            text = item.desc,
            style = Typography.bodyMedium,
            color = AppTheme.colors.onBackground
        )
    }
}


@Composable
fun MyProfileListItem(
    modifier: Modifier = Modifier,
    list: List<MyProfileItem>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(list.size) { index ->
            MyProfileItem(item = list[index])
            Divider(
                thickness = 1.dp,
                color = AppTheme.colors.onBackground
            )
        }
    }
}

data class MyProfileItem(
    val title: String,
    val desc: String
)

val exampleMyProfileList = listOf(
    MyProfileItem(
        title = "Full Name",
        desc = "John Doe"
    ),
    MyProfileItem(
        title = "Full Name",
        desc = "John Doe"
    ),
    MyProfileItem(
        title = "Full Name",
        desc = "John Doe"
    ),
    MyProfileItem(
        title = "Full Name",
        desc = "John Doe"
    )
)