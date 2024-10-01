package com.example.worktimetracker.ui.screens.profile.component

import androidx.annotation.StringRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionTopBar(
    @StringRes title: Int,
    onBack: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null
                )
            }
        },
        title = {
            Text(text = stringResource(id = title), style = Typography.titleLarge.copy(
                color = colorResource(R.color.text)
            ))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.topbar_bg),
            titleContentColor = colorResource(id = R.color.black),
        ),
    )
}