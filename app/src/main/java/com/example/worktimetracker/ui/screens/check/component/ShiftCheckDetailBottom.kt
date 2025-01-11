package com.example.worktimetracker.ui.screens.check.component

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.helper.ISOFormater
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.util.BiometricPromptManager
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.HourglassStart
import compose.icons.fontawesomeicons.solid.UserClock

@Composable
fun ShiftCheckDetailBottom(
    shift: Shift,
    btnOnclick: ( checkType: Int ) -> Unit
) {



    Column(
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ShiftCardItem(
                title = stringResource(R.string.start_time),
                text = ISOFormater.formatDateTimeToTime(shift.start),
                icon = FontAwesomeIcons.Solid.HourglassStart
            )
            ShiftCardItem(
                title = stringResource(R.string.check_in),
                text =  ISOFormater.formatDateTimeToTime(shift.checkIn),
                icon = FontAwesomeIcons.Solid.UserClock
            )
            ShiftCardItem(
                title = stringResource(R.string.end_time),
                text = ISOFormater.formatDateTimeToTime(shift.end),
                icon = FontAwesomeIcons.Solid.UserClock
            )
            ShiftCardItem(
                title = stringResource(R.string.check_out),
                text =  ISOFormater.formatDateTimeToTime(shift.checkOut),
                icon = FontAwesomeIcons.Solid.UserClock
            )
        }

        Row (

        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(4.dp),
                onClick = { btnOnclick(0) },
                content = {
                    Text(stringResource(R.string.check_in))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.hightlightSurface
                )
            )
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(4.dp),
                onClick = { btnOnclick(1) },
                content = {
                    Text(stringResource(R.string.check_out))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.8f)
                )
            )
        }
    }
}




