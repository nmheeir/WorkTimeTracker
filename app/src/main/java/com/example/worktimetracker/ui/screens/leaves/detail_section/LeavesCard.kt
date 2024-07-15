package com.example.worktimetracker.ui.screens.leaves.detail_section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.LeavesDetail
import com.example.worktimetracker.ui.util.lLeavesDetail


/* for upcoming and past leaves card
* */
//@Preview(showBackground = true)
@Composable
fun LeavesCard(
    modifier: Modifier = Modifier,
    leavesDetail: LeavesDetail = lLeavesDetail[0]
) {
    val textColor =
        if (leavesDetail.isApproved) colorResource(id = R.color.blue) else colorResource(id = R.color.red)
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = textColor.copy(alpha = 0.2f))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Date",
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Normal
                )
                Text(text = leavesDetail.date, style = Typography.labelLarge)
            }
            Text(
                text = if (leavesDetail.isApproved) "Approved" else "Rejected",
                color = textColor.copy(alpha = 0.8f),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = textColor.copy(alpha = 0.2f)
                    )
                    .padding(8.dp)
            )
        }
        Divider(
            thickness = 1.dp,
            color = colorResource(id = R.color.black).copy(alpha = 0.5f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Apply Days",
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = leavesDetail.applyDays.toString().plus(" days"),
                    style = Typography.titleMedium
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Leave Balance",
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = leavesDetail.leaveBalance.toString(),
                    style = Typography.titleMedium

                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Approved By",
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = leavesDetail.approvedBy.userName,
                    style = Typography.titleMedium
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListLeavesCard(
    modifier: Modifier = Modifier,
    listLeavesDetail: List<LeavesDetail> = lLeavesDetail
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(listLeavesDetail.size) {
            LeavesCard(leavesDetail = listLeavesDetail[it])
        }
    }
}

