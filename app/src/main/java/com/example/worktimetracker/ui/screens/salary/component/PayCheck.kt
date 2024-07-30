package com.example.worktimetracker.ui.screens.salary.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PayCheckList(modifier: Modifier = Modifier) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(10) {
            PayCheckItem()
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun PayCheckItem(modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(

        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "June 2024 Paycheck",
                style = Typography.bodyLarge,
                color = colorResource(id = R.color.blue)
            )
            Text(
                text = "From 26/05/2024 to 25/06/2024",
                style = Typography.bodyMedium
            )
            Text(
                text = "View Detail",
                style = Typography.bodySmall,
                color = colorResource(id = R.color.blue),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        Toast
                            .makeText(context, "View Detail", Toast.LENGTH_SHORT)
                            .show()
                    }
            )
        }
    }
}
