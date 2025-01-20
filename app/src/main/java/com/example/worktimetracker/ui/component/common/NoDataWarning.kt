package com.example.worktimetracker.ui.component.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.AppTheme

@Composable
@Preview
fun NoDataWarning(
    modifier: Modifier = Modifier,
    description: String = stringResource(R.string.no_data)
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.nodata),  // Tải ảnh từ drawable
            contentDescription = "No data",  // Mô tả cho ảnh
        )
        Text(
            text = description,
            color = AppTheme.colors.blurredText
        )
    }

}