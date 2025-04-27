package com.example.worktimetracker.ui.screens.check.checkPage

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.core.presentation.util.ObserveAsEvents
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.ui.component.common.NoDataWarning
import com.example.worktimetracker.ui.component.dialog.SuccessDialog
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.screens.check.component.DigitalClock
import com.example.worktimetracker.ui.screens.check.component.MapContent
import com.example.worktimetracker.ui.screens.check.component.ShiftCard
import com.example.worktimetracker.ui.screens.check.component.ShiftCheckDetailBottom
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.util.BiometricPromptManager
import kotlinx.coroutines.flow.Flow

@Composable
fun CheckScreen(
    state: CheckUiState,
    channel: Flow<CheckUiEvent>,
    action: (CheckUiAction) -> Unit,
    onCheckSuccess: (Route) -> Unit,
    onNavigateTo: (Route) -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val promptManager = BiometricPromptManager(context as AppCompatActivity)
    val biometricResult by promptManager.promptResults.collectAsState(initial = null)
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            println("Activity result: $it")
        }
    )

    LaunchedEffect(biometricResult) {
        if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet) {
            if (Build.VERSION.SDK_INT >= 30) {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                enrollLauncher.launch(enrollIntent)
            }
        }
    }

    biometricResult?.let { result ->
        when (result) {
            is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                Log.d("CheckScreen", "Lỗi biometric")
            }

            BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                Log.d("CheckScreen", "Lỗi AuthenticationFailed")
            }

            BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                Log.d("CheckScreen", "Lỗi AuthenticationNotSet")
            }

            is BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                Log.d("CheckScreen", "AuthenticationSuccess")
            }

            BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                Log.d("CheckScreen", "Lỗi FeatureUnavailable")
            }

            BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                Log.d("CheckScreen", "Lỗi HardwareUnavailable")
            }
        }
    }


    // Dialog
    var dialogContent by remember  { mutableStateOf("") }
    var isSuccess by remember  { mutableStateOf(true) }
    var isVisible by remember  { mutableStateOf(false) }
    ObserveAsEvents(channel) {
        when(it) {
            CheckUiEvent.CheckSuccess -> {
                dialogContent = context.getString(R.string.check_success)
                isVisible = true
            }

            is CheckUiEvent.Failure -> {
                dialogContent = it.message
                isSuccess = false
                isVisible = true
            }

            CheckUiEvent.Success -> {

            }
        }
    }

    if(isVisible) {
        SuccessDialog(isSuccess, dialogContent, { isVisible = false })
    }

    // Content
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 0.dp
                )
        ) {
            // Header
            item {
                Header()
            }

            // Digital Clock
            item {
                DigitalClock()
            }

            // Shifts Section
            item {
                Text(
                    text = stringResource(R.string.your_shift),
                    style = MaterialTheme.typography.headlineMedium,
                    color = AppTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            item {
                Box {
                    AnimatedVisibility(
                        visible = state.choosenShift == null,
                        enter = slideInHorizontally(
                            initialOffsetX = { -it - 40 },
                            animationSpec = tween(
                                delayMillis = 500,
                                durationMillis = 300
                            )
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(
                                durationMillis = 300
                            )
                        )
                    ) {
                        when {
                            state.isShiftLoading -> {
                                CircularProgressIndicator(
                                    color = AppTheme.colors.blurredText,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }
                            state.todayShifts.isEmpty() -> {
                                NoDataWarning()
                            }
                            else -> {
                                Column {
                                    state.todayShifts.forEach { shift ->
                                        ShiftCard(
                                            shift,
                                            onClick = { action(CheckUiAction.ChooseShift(shift)) }
                                        )
                                    }
                                }
                            }
                        }

                    }
                    AnimatedVisibility(
                        visible = state.choosenShift != null,
                        enter = slideInHorizontally(
                            initialOffsetX = { it + 40 },
                            animationSpec = tween(
                                delayMillis = 500
                            )
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = 300
                            )
                        ),
                    ) {
                        MapContent(
                        ) { currentLocation ->
                            action(CheckUiAction.UpdateCurrentLocation(currentLocation))
                        }
                    }
                }
            }
        }
        // Bottom controll
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            AnimatedVisibility(
                visible = state.choosenShift != null,
                enter = slideInVertically(
                    initialOffsetY = { height -> height }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it + 200 }
                )
            ) {
                Box(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp
                            )
                        )
                        .background(
                            color = AppTheme.colors.regularSurface
                        )
                        .padding(vertical = 40.dp)
                ) {
                    ShiftCheckDetailBottom(
                        state.choosenShift?: Shift(0,0, "", "","","",0.0f,0)
                    ) { checkType ->
                        promptManager.showBiometricPrompt(
                            title = "Sample prompt",
                            desc = "Sample prompt description"
                        ) {
                            action(CheckUiAction.Check(checkType))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.check_screen),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
    }
}

