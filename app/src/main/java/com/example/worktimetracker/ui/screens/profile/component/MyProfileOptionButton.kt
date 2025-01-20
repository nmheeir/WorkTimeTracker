package com.example.worktimetracker.ui.screens.profile.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography

@Composable
fun MyProfileOptionButton(
    modifier: Modifier = Modifier,
    title: String = "Personal",
    isClick: Boolean,
    onClick: () -> Unit = {}
) {
    val containerColor =
        if (isClick) AppTheme.colors.actionSurface else Color.Transparent
    val contentColor =
        if (isClick) AppTheme.colors.onBackground else AppTheme.colors.blurredText
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = modifier
    ) {
        Text(
            text = title,
            style = Typography.labelMedium
        )
    }
}

val lOptionMyProfile = listOf(
    "Personal",
    "Professional",
    "Documents"
)

