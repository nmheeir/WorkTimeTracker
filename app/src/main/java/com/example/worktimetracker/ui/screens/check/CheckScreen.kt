package com.example.worktimetracker.ui.screens.check


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.ui.navigation.Route
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography


@Composable
fun CheckScreen(
    viewModel: CheckViewModel,
    onCheckSuccess: (Route) -> Unit,
    onNavigateTo: (Route) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.checkUiEvent.collect {
            when (it) {
                is ApiResult.Success -> {
                    onCheckSuccess(Route.HomeScreen)
                }

                is ApiResult.Error -> {
                    Log.d("CheckSrceen", "Lỗi api")
                }

                is ApiResult.NetworkError -> {
                    //nothing
                }
            }
        }
    }

    CheckContent(
        onEvent = viewModel::onEvent,
        onNavigateTo = {
            onNavigateTo(it)
        }
    )
}

@Composable
fun CheckContent(
    onEvent: (CheckUiEvent) -> Unit,
    onNavigateTo: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = { onEvent(CheckUiEvent.CheckIn) } ,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue)
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Test",
                style = Typography.titleMedium,
                color = colorResource(id = R.color.white)
            )
        }
    }
}
