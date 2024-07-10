package com.example.worktimetracker.ui.screens.home.attendance_section

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
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
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.poppinsFontFamily

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AttendanceSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        Text(
            text = "Today Attendance",
            style = Typography.labelLarge,
            modifier = modifier.padding(vertical = 8.dp)
        )
        LazyHorizontalGrid(
            horizontalArrangement = Arrangement.SpaceBetween,
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(4) {
                AttendanceItem()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AttendanceItem(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .padding(8.dp)
//            .height(120.dp)
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, colorResource(id = R.color.sub_color), RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.white))
            .padding(8.dp)
    ) {
        val (title, detail) = createRefs()

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    top.linkTo(parent.top)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )

            Text(
                text = "Check In",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(detail) {
                    top.linkTo(title.bottom, margin = 4.dp)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Text(
                text = "10:20 am",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "On time",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}