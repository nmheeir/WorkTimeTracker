package com.example.worktimetracker.ui.screens.furlough.detail_section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.home.components.Avatar
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.LeavesDetail
import com.example.worktimetracker.ui.util.lLeavesDetail

@Composable
fun TeamLeavesCard(
    modifier: Modifier = Modifier,
    leavesDetail: LeavesDetail = lLeavesDetail[0],
    onRejectClick: () -> Unit = {},
    onAcceptClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.white))
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Avatar(
                image = R.drawable.avatar,
                modifier = Modifier.size(48.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = leavesDetail.approvedBy.userName,
                    style = Typography.labelLarge
                )
                Text(text = leavesDetail.date,
                    style = Typography.displayMedium)
            }
        }
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            LeavesButton(
                modifier = Modifier.weight(1f),
            )
            LeavesButton(
                type = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun LeavesButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    type: Boolean = false
) {
    val icon = if (type) R.drawable.ic_approved else R.drawable.ic_reject
    val text = if (type) "Approved" else "Rejected"
    val backgroundColor =
        if (!type) colorResource(id = R.color.red) else colorResource(id = R.color.teal)
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor.copy(alpha = 0.8f)
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Text(text = text, style = Typography.displayMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListTeamLeaves(modifier: Modifier = Modifier) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(lLeavesDetail.size) { index ->
            TeamLeavesCard(leavesDetail = lLeavesDetail[index])
        }
    }
}