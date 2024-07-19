package com.example.worktimetracker.ui.screens.home.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun HomeOptionItem(
    modifier: Modifier = Modifier,
    item: HomeOptionItem = listHomeOption[0],
    onClick: (Route) -> Unit = {}
) {
    val iconColor = colorResource(id = item.color)
    val bgColor = colorResource(id = item.color).copy(alpha = 0.2f)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .wrapContentHeight()
            .clickable {
                onClick(item.route)
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
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
            text = item.route.route,
            style = Typography.labelMedium,
            fontWeight = FontWeight.Normal
        )
    }
}

data class HomeOptionItem(
    val route: Route,
    @DrawableRes val icon: Int,
    @ColorRes val color: Int
)

val listHomeOption = listOf(
    HomeOptionItem(
        route = Route.ProfileScreen,
        icon = R.drawable.ic_user_filled,
        color = R.color.blue
    ),
    HomeOptionItem(
        route = Route.CheckInScreen,
        icon = R.drawable.ic_clock_filled,
        color = R.color.teal
    ),
    HomeOptionItem(
        route = Route.ProfileScreen,
        icon = R.drawable.ic_user,
        color = R.color.purple_200
    ),
    HomeOptionItem(
        route = Route.ProfileScreen,
        icon = R.drawable.ic_user,
        color = R.color.red
    ),
)