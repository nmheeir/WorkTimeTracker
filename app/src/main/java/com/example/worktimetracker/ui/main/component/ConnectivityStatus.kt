package com.example.worktimetracker.ui.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.helper.ConnectionState
import com.example.worktimetracker.helper.connectivityState
import com.example.worktimetracker.ui.theme.Typography
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun ConnectivityStatusBox(
    isConnect: Boolean = true
) {
    val bgColor by animateColorAsState(
        targetValue = if (isConnect) colorResource(id = R.color.teal).copy(alpha = 0.9f) else colorResource(
            id = R.color.red
        ).copy(alpha = 0.9f),
        label = "ConnectStatusBox"
    )
    val icon = if (isConnect) R.drawable.ic_cloud_connect else R.drawable.ic_cloud_disconnect
    val message = if (isConnect) R.string.connected else R.string.disconnected
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(bgColor)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = colorResource(id = R.color.white)
            )
            Text(
                text = stringResource(id = message),
                style = Typography.bodySmall,
                color = colorResource(id = R.color.white)
            )
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ConnectivityStatus() {
    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available
    var visibility by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        ConnectivityStatusBox(isConnect = isConnected)
    }

    LaunchedEffect(isConnected) {
        visibility = if (!isConnected) {
            true
        } else {
            delay(2000)
            false
        }
    }
}