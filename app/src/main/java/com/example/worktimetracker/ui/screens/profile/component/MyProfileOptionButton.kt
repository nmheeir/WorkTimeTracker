package com.example.worktimetracker.ui.screens.profile.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.worktimetracker.R

@Preview(showBackground = true)
@Composable
fun MyProfileOptionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue),
            contentColor = colorResource(id = R.color.white)
        ),
        modifier = modifier
    ) {
//        Text(text = )
    }
}