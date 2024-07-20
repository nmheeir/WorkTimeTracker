package com.example.worktimetracker.ui.screens.furlough.detail_section

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.lLeavesDetail

@Preview(showBackground = true)
@Composable
fun LeavesDetailSection(modifier: Modifier = Modifier) {
    var selectedIndex by remember {
        mutableStateOf(lDetailOption[0])
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(colorResource(id = R.color.light_gray))
        ) {
            items(lDetailOption) {
                LeavesChipDetail(
                    text = it,
                    isSelect = it == selectedIndex,
                    onClick = {
                        selectedIndex = it
                    }
                )
            }
        }
        when (selectedIndex) {
            lDetailOption[0] -> {
                val leavesList = lLeavesDetail.filter { it.isApproved }
                ListLeavesCard(listLeavesDetail = leavesList)
            }
            lDetailOption[1] -> {
                val leavesList = lLeavesDetail.filter { !it.isApproved }
                ListLeavesCard(listLeavesDetail = leavesList)
            }
            lDetailOption[2] -> {
                ListTeamLeaves()
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun LeavesChipDetail(
    modifier: Modifier = Modifier,
    text: String = "Upcoming",
    isSelect: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelect) colorResource(id = R.color.blue) else colorResource(id = R.color.light_gray),
        animationSpec = TweenSpec(200),
        label = "background color"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelect) colorResource(id = R.color.white) else colorResource(id = R.color.black),
        animationSpec = TweenSpec(500),
        label = "text color"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                color = backgroundColor
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = text,
            style = Typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = textColor
        )
    }
}

val lDetailOption = listOf(
    "Upcoming",
    "Past",
    "Team Leave"
)