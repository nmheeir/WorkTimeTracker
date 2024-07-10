package com.example.worktimetracker.ui.screens.home.activity_section

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.poppinsFontFamily

@Preview(showBackground = true)
@Composable
fun ActivitySection(modifier: Modifier = Modifier) {
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
                color = colorResource(id = R.color.blue)
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(10) {
                ActivitySectionItem(number = it)
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun ActivitySectionItem(modifier: Modifier = Modifier, number: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, colorResource(id = R.color.light_gray), RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.white))
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
                    text = "Check In $number",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "April 17, 2023",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Light
                )
            }
            Column {
                Text(
                    text = "10:00 am",
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