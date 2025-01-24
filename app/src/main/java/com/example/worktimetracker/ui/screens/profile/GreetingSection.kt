package com.example.worktimetracker.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.home.components.Avatar
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiEvent
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.StorageUtil.Companion.uploadAvatarToStorage
import kotlinx.coroutines.launch

@Composable
fun GreetingSection(
    modifier: Modifier = Modifier,
    state: SharedUiState,
    event: (SharedUiEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val avatarUri = try {
        state.user.avatarURL.toUri()
    } catch (e: Exception) {
        null // Trả về Uri rỗng nếu URL sai
    }
    var uri: Uri? by remember {
        mutableStateOf(avatarUri)
    }
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            selectedUri?.let {
                uri = it
                coroutineScope.launch {
                    val downloadUrl = uploadAvatarToStorage(it, context, state.user.id)
                    if (downloadUrl != null) {
                        event(SharedUiEvent.UploadImage(downloadUrl))
                    }
                }
            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box {
            Avatar(
                avatarUrl = uri,
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.Center),
                onClick = {
                    //Do nothing
                }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
                tint = AppTheme.colors.onBackground,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        color = AppTheme.colors.onBackground,
                        width = 1.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(colorResource(id = R.color.blue))
                    .padding(6.dp)
                    .clickable {
                        singlePhotoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            )
        }
        Text(
            text = state.user.userName,
            style = Typography.titleLarge,
            color = AppTheme.colors.onBackground
        )
        Text(
            text = state.user.department,
            style = Typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = AppTheme.colors.onBackground
        )
    }
}