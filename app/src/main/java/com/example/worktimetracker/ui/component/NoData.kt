package com.example.worktimetracker.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.worktimetracker.R

@Composable
fun NoDataWarning(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.nodata),  // Tải ảnh từ drawable
        contentDescription = "No data",  // Mô tả cho ảnh
        modifier = modifier.fillMaxSize() // Điều chỉnh kích thước hoặc vị trí nếu cần
    )
}