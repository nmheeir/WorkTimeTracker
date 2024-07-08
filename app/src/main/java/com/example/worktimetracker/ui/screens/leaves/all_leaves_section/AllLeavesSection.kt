package com.example.worktimetracker.ui.screens.leaves.all_leaves_section

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.Leaves
import com.example.worktimetracker.ui.util.listLeaves

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AllLeavesSection(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        AllLeavesHeader()
        LazyVerticalGrid(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(listLeaves.size) {
                LeavesCard(leave = listLeaves[it], cardBackground = cardColor[it])
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun AllLeavesHeader(
    modifier: Modifier = Modifier,
    addLeaves: () -> Unit = {},
    filterLeaves: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "All Leaves",
            style = Typography.labelLarge
        )
        Row {
            IconButton(onClick = addLeaves) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_square),
                    contentDescription = null
                )
            }
            IconButton(onClick = filterLeaves) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = null
                )
            }
        }
    }
}

@SuppressLint("ResourceType")
//@Preview(showBackground = true)
@Composable
fun LeavesCard(
    modifier: Modifier = Modifier,
    leave: Leaves = listLeaves[0],
    @DrawableRes cardBackground: Int = cardColor[1]
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .height(140.dp)
            .aspectRatio(1.5f)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, color = colorResource(id = cardBackground), RoundedCornerShape(16.dp))
            .background(color = colorResource(id = cardBackground).copy(alpha = 0.2f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = leave.title,
            style = Typography.labelLarge,
            maxLines = 2
        )
        Text(
            text = leave.count.toString(),
            style = Typography.labelLarge,
            color = colorResource(id = cardBackground)
        )
    }
}

val cardColor = listOf(
    R.color.blue,
    R.color.green,
    R.color.teal,
    R.color.red
)