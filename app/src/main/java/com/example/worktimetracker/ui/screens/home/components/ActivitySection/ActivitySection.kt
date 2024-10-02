package com.example.worktimetracker.ui.screens.home.components.ActivitySection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.poppinsFontFamily

@Composable
fun ActivitySection(
    viewModel: ActivitySectionViewModel,
    modifier: Modifier = Modifier,
    state: ActivitySectionUiState,
    onNavigateTo: (Route) -> Unit = {}
) {

    var checkList: List<Check> by remember {
        mutableStateOf(emptyList())
    }

    LaunchedEffect(state) {
        checkList = state.activityItems
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Your Activity",
                style = Typography.labelLarge
            )
            Text(
                text = "View All",
                style = Typography.labelLarge,
                color = colorResource(id = R.color.blue),
                modifier = Modifier.clickable { onNavigateTo(Route.ActivitySrceen) }
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(checkList.size) {
                ActivitySectionItem(item = checkList[it])
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun ActivitySectionItem(modifier: Modifier = Modifier, item: Check) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, colorResource(id = R.color.light_gray), RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.check_item_bg))
            .padding(16.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.light_gray))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .weight(1f)
        ) {
            Column {
                Text(
                    text = item.checkType(),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = item.getDateForCheck(),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Light
                )
            }
            Column {
                Text(
                    text = item.getHour(),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "On time",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}