package com.example.worktimetracker.ui.screens.check


import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.BiometricPromptManager


@Composable
fun CheckScreen(
    viewModel: CheckViewModel,
    onCheckSuccess: (Route) -> Unit,
    onNavigateTo: (Route) -> Unit,
    promptManager: BiometricPromptManager
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
    
    val biometricResult by promptManager.promptResults.collectAsState(
        initial = null
    )
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

    // TODO: biometric handle
    biometricResult?.let { result ->
        when (result) {
            is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                Log.d("CheckSrceen", "Lỗi biometric")
            }

            BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                Log.d("CheckSrceen", "Lỗi AuthenticationFailed")
            }

            BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                Log.d("CheckSrceen", "Lỗi AuthenticationNotSet")
            }

            BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                Log.d("CheckSrceen", "AuthenticationSuccess")
            }

            BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                Log.d("CheckSrceen", "Lỗi FeatureUnavailable")
            }

            BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                Log.d("CheckSrceen", "Lỗi HardwareUnavailable")
            }
        }
    }


    CheckContent(
        onEvent = viewModel::onEvent,
        onNavigateTo = {
            onNavigateTo(it)
        },
        promptManager = promptManager
    )
}

@Composable
fun CheckContent(
    onEvent: (CheckUiEvent) -> Unit,
    onNavigateTo: (Route) -> Unit,
    modifier: Modifier = Modifier,
    promptManager: BiometricPromptManager
) {
    Button(
        onClick = { onEvent(CheckUiEvent.CheckIn) },
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

    Button(
        onClick = {
            promptManager.showBiometricPrompt(
                title = "Sample prompt",
                desc = "Sample prompt description"
            )
        }
    ) {
        Text(text = "Test biometric")
    }
}
