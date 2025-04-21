package com.example.worktimetracker.ui.screens.auth.forgotpw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.core.presentation.util.ObserveAsEvents
import com.example.worktimetracker.ui.component.common.GlowingButton
import com.example.worktimetracker.ui.component.dialog.SuccessDialog
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import kotlinx.coroutines.flow.Flow
import android.content.Intent
import android.widget.Toast
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.worktimetracker.ui.viewmodels.ForgotPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateTo: (Screens) -> Unit,
    onBack: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    var isVisible by remember { mutableStateOf(false) }
    var dialogContent by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(true) }
    ObserveAsEvents(viewModel.channel) {
        when (it) {
            ForgotPasswordUiEvent.NotFoundUser -> {
                isVisible = true
                isSuccess = false
                dialogContent = context.getString(R.string.username_not_found)
            }

            ForgotPasswordUiEvent.SendRequestSuccess -> {
                isVisible = true
                isSuccess = true
                dialogContent = context.getString(R.string.reset_pw_success_instruction)
            }

            ForgotPasswordUiEvent.UnknownError -> {
                isVisible = true
                isSuccess = false
                dialogContent = context.getString(R.string.unknown_err)
            }
        }
    }
    if (isVisible) {
        SuccessDialog(
            isSuccess,
            dialogContent,
            { isVisible = false },
            {
                try {
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        `package` = "com.google.android.gm"
                    }
                    context.startActivity(intent)
                } catch (ex: Exception) {
                    Toast.makeText(context, "Gmail app is not installed", Toast.LENGTH_SHORT).show()
                }

            },
            "Open Gmail"
        )
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Forgot Password",
                        style = MaterialTheme.typography.titleLarge,
                        color = AppTheme.colors.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null,
                            tint = AppTheme.colors.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(paddingValues)
        ) {
            ForgotPasswordDetail(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            )
            ForgotPasswordEmailForm(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                state = state,
                action = viewModel::onAction
            )
            GlowingButton(
                onClick = {
                    viewModel.onAction(ForgotPasswordUiAction.SendRequest)
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
            ) {
                Text(
                    text = stringResource(R.string.send_request),
                    style = Typography.labelMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun ForgotPasswordDetail(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.forgot_pw),
            style = Typography.labelLarge,
            color = AppTheme.colors.onBackground
        )
        Text(
            text = stringResource(R.string.forgot_pw_instruction),
            style = Typography.bodyMedium,
            color = AppTheme.colors.onBackground
        )
    }
}

@Composable
private fun ForgotPasswordEmailForm(
    modifier: Modifier = Modifier,
    action: (ForgotPasswordUiAction) -> Unit,
    state: ForgotPasswordUiState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.email_address),
            style = Typography.labelLarge,
            color = AppTheme.colors.onBackground
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = {
                action(ForgotPasswordUiAction.OnEmailChange(it))
            },
            isError = state.isError,
            supportingText = {
                if (state.isError) {
                    Text(text = state.error, color = AppTheme.colors.onBackground)
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppTheme.colors.onBackground,
                focusedBorderColor = AppTheme.colors.onBackground,
                focusedTextColor = AppTheme.colors.onBackground
            )
        )
    }
}