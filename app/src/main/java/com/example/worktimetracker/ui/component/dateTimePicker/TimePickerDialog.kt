package com.example.worktimetracker.ui.component.dateTimePicker

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    event: (LocalTime?) -> Unit,
    showDialog: () -> Boolean,
    selectedTime: LocalTime?
) {
    // Dùng remember để giữ trạng thái của selectedTime
    val _selectedTime = remember { mutableStateOf(selectedTime ?: LocalTime.of(8, 20, 0)) }

    ClockDialog(
        state = rememberUseCaseState(
            visible = showDialog(),
            onDismissRequest = {
                Log.d("Log CLock Dialog", "onDismissRequest")
            },
            onCloseRequest = {
                Log.d("Log CLock Dialog", "onCloseRequest")
            },
            onFinishedRequest = {
                event(_selectedTime.value)
                Log.d("Log CLock Dialog", "selectedTime: ${_selectedTime.value}")
            }
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            // Cập nhật _selectedTime
            _selectedTime.value = LocalTime.of(hours, minutes, 0)
        },
        config = ClockConfig(
            boundary = LocalTime.of(0, 0, 0)..LocalTime.of(23, 59, 0),
            defaultTime = _selectedTime.value, // Sử dụng giá trị từ _selectedTime
            is24HourFormat = true
        ),
    )
}
