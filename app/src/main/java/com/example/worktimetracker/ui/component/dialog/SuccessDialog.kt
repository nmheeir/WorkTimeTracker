package com.example.worktimetracker.ui.component.dialog

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.worktimetracker.ui.theme.AppTheme
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Check
import compose.icons.fontawesomeicons.solid.Times


@SuppressLint("SuspiciousIndentation")
@Composable
fun SuccessDialog(
    isSuccess: Boolean,
    description: String = "",
    onHide : () -> Unit,
    onSubmit: () -> Unit = onHide,
    buttonText: String = "Ok"
) {
    var isCardFlipped by remember { mutableStateOf(false) }
    val animDuration = 1000
    val zAxisDistance = 10f
    LaunchedEffect(Unit) {
        isCardFlipped = true
    }
    // rotate Y-axis with animation
    val rotateCardY by animateFloatAsState(
        targetValue = if (isCardFlipped) 720f else 0f,
        animationSpec = tween(durationMillis = animDuration, easing = EaseInOut),
        label = ""
    )

    // icon
    val imageVector = if(isSuccess) FontAwesomeIcons.Solid.Check else FontAwesomeIcons.Solid.Times
    val backgroundIcon = if(isSuccess) Color.Green else Color.Red

    // size
    val width = LocalConfiguration.current.screenWidthDp - 40
    val maxHeight = LocalConfiguration.current.screenHeightDp / 3
        Dialog(
            onDismissRequest = { onHide() }
        ) {
            Box(
                modifier = Modifier
                    .width(width.dp)
                    .heightIn(maxHeight.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = "Icon",
                        modifier = Modifier.size(48.dp)
                            .clip(RoundedCornerShape(48.dp))
                            .background(backgroundIcon)
                            .padding(10.dp)
                            .graphicsLayer {
                                rotationY = rotateCardY
                                cameraDistance = zAxisDistance
                            },
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = description,
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { onSubmit() },
                        colors = ButtonColors(
                            contentColor = AppTheme.colors.onActionSurface,
                            containerColor = AppTheme.colors.actionSurface,
                            disabledContainerColor = AppTheme.colors.actionSurface,
                            disabledContentColor = AppTheme.colors.onActionSurface
                        )
                    ) {
                        Text(buttonText)
                    }
                }
            }
        }

}