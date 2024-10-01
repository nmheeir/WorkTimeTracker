package com.example.worktimetracker.ui.screens.shift

import android.R.attr.fontFamily
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.worktimetracker.R
import com.example.worktimetracker.helper.Helper
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.poppinsFontFamily


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShiftDialog(
    viewModel: ShiftViewModel
) {
    Dialog(onDismissRequest = { viewModel.onEvent(ShiftUiEvent.DialogToggle(0)) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.your_shift),
                    style = Typography.titleLarge
                )

                val shifts =
                    viewModel.state.shiftMap?.get(viewModel.state.dialogDate) ?: emptyList()
                FlowRow {
                    for (shift in shifts) {
                        Box(
                            modifier = Modifier
                                .padding(2.dp) // Margin xung quanh Box
                                .background(color = colorResource(R.color.green_bg)) // Áp dụng màu nền
                                .padding(2.dp) // Padding bên trong Box
                        ) {
                            Text(
                                text = Helper.convertMillisToTimeStamp(shift.start) + " - " + Helper.convertMillisToTimeStamp(
                                    shift.end
                                ),
                                style = Typography.bodyMedium.copy(
                                    color = colorResource(R.color.green), // Màu chữ
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                        }
                    }
                }

                Text(
                    text = stringResource(id = R.string.your_check),
                    style = Typography.titleLarge
                )

                val checks =
                    viewModel.state.checkMap?.get(viewModel.state.dialogDate) ?: emptyList()
                FlowRow {
                    for (check in checks) {
                        Box(
                            modifier = Modifier
                                .padding(2.dp) // Margin xung quanh Box
                                .background(color = colorResource(R.color.orange_bg)) // Áp dụng màu nền
                                .padding(2.dp) // Padding bên trong Box
                        ) {
                            Text(
                                text = Helper.convertMillisToTimeStamp(check.checkTime),
                                style = Typography.bodyMedium.copy(
                                    color = colorResource(R.color.orange), // Màu chữ
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                        }
                    }
                }

            }
        }
    }
}

