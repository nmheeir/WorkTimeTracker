package com.example.worktimetracker.ui.screens.home.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun HomeOptionItem(
    modifier: Modifier = Modifier,
    item: HomeOptionItemData = listHomeOption[0],
    onClick: ((Screens) -> Unit)? = null,
    onShowDialog: () -> Unit = {}
) {
    val iconColor = colorResource(id = item.color)
    val bgColor = colorResource(id = item.color).copy(alpha = 0.2f)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .wrapContentSize()
            .clickable {
                onClick?.invoke(item.screens!!) ?: onShowDialog()
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(color = bgColor)
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
        Text(
            text = item.title,
            style = Typography.labelMedium,
            fontWeight = FontWeight.Normal,
            color = AppTheme.colors.onHighlightSurface
        )
    }
}

data class HomeOptionItemData(
    val screens: Screens? = null,
    val title: String,
    @DrawableRes val icon: Int,
    @ColorRes val color: Int
)

// TODO: sửa lại list home option
val listHomeOption = listOf(
    HomeOptionItemData(
        screens = Screens.ProfileScreen,
        title = "Profile",
        icon = R.drawable.ic_user_filled,
        color = R.color.blue
    ),
    HomeOptionItemData(
        screens = Screens.CheckScreen,
        title = "Check",
        icon = R.drawable.ic_clock_filled,
        color = R.color.teal
    ),
    HomeOptionItemData(
        screens = Screens.LogScreen,
        title = "Logging",
        icon = R.drawable.ic_add_square,
        color = R.color.purple_200
    ),
    HomeOptionItemData(
        screens = Screens.WorkTimeScreen,
        title = "Work Chart",
        icon = R.drawable.ic_chart,
        color = R.color.yellow
    ),
    HomeOptionItemData(
        screens = Screens.SalaryScreen,
        title = "Salary",
        icon = R.drawable.ic_dollar_money,
        color = R.color.green
    ),
    HomeOptionItemData(
        screens = Screens.ShiftScreen,
        title = "Shift",
        icon = R.drawable.ic_calendar,
        color = R.color.green
    ),
    HomeOptionItemData(
        screens = Screens.TaskScreen,
        title = "Task",
        icon = R.drawable.ic_date_range,
        color = R.color.orange
    )
)