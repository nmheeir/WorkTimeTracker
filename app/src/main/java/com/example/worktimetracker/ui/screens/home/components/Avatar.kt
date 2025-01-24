package com.example.worktimetracker.ui.screens.home.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.worktimetracker.R

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    avatarUrl: Uri?,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.white),
                shape = CircleShape
            )
    ) {
        if (avatarUrl == null) {
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop
            )
        }
        else {
            val imageRequest = ImageRequest.Builder(context)
                .data(avatarUrl)
                .crossfade(true)
                .build()
            AsyncImage(
                model = imageRequest,
                contentDescription = "avatar",
                placeholder = painterResource(id = R.drawable.ic_user),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable {
                        onClick()
                    }
            )
        }
    }
}