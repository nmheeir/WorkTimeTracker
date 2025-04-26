package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.remote.enums.CheckType
import com.example.worktimetracker.data.remote.response.CheckInfo
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.data.remote.response.ShiftType
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.use_case.shift.ShiftUseCase
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import com.example.worktimetracker.ui.util.exampleUser
import com.skydoves.sandwich.getOrThrow
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userUseCase: UserUseCase,
    private val shiftUseCase: ShiftUseCase
) : ViewModel() {

    private val token = context.dataStore.get(TokenKey, "")

    val isLoading = MutableStateFlow(false)

    val user = MutableStateFlow<User?>(null)
    val todayShifts = MutableStateFlow<List<Shift>>(fakeShifts)
    val currentWeekDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val checkInfos = MutableStateFlow<List<CheckInfo>>(fakeCheckInfos)

    init {
        viewModelScope.launch {
            currentWeekDates.value = getCurrentWeekDates()
            fetchUserProfile()
            getTodayShift()
            getUserActivities()
        }
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            userUseCase.getUserProfile(token)
                .suspendOnSuccess {
                    user.value = this.data.data
                    Timber.d(this.data.data.toString())
                }
                .suspendOnFailure {
                    Timber.d("Failure: ${this.message()}")
                }
                .suspendOnException {
                    Timber.d("Exception: $message")
                }
        }
    }

    private fun getCurrentWeekDates(): List<LocalDate> {
        val today = LocalDate.now()
        val monday = today.with(DayOfWeek.MONDAY)
        return (0..6).map { monday.plusDays(it.toLong()) }
    }

    private fun getTodayShift() {
        viewModelScope.launch {
            shiftUseCase.getMyShift(
                token = token,
                start = LocalDateTime.now().with(LocalTime.MIN),
                end = LocalDateTime.now().with(LocalTime.MAX)
            )
                .suspendOnSuccess {
                    if (this.data.data.isNullOrEmpty()) {
                        todayShifts.value = fakeShifts
                    } else {
                        todayShifts.value = this.data.data ?: fakeShifts
                    }
                    Timber.d(this.data.data.toString())
                }
                .suspendOnException {
                    Timber.d(this.message())
                }
                .suspendOnFailure {
                    Timber.d(this.message())
                }
        }
    }

    private fun getUserActivities() {
        viewModelScope.launch {
            userUseCase.getUserActivity(
                token = token,
                start = LocalDateTime.now().with(LocalTime.MIN),
                end = LocalDateTime.now().with(LocalTime.MAX)
            ).suspendOnSuccess {
                checkInfos.value = this.data.data?.checkInfos ?: fakeCheckInfos
                Timber.d(this.data.data.toString())
            }.suspendOnException {
                Timber.d(this.message())
            }
        }
    }
}

val fakeShifts = listOf(
    Shift(
        id = 1,
        start = LocalDateTime.parse("2024-04-01T09:00:00"),
        end = LocalDateTime.parse("2024-04-01T17:00:00"),
        checkIn = LocalDateTime.parse("2024-04-01T09:05:00"),
        checkOut = LocalDateTime.parse("2024-04-01T16:55:00"),
        workDuration = 7.8f,
        shiftType = ShiftType.entries.random(),
        user = exampleUser
    ),
    Shift(
        id = 2,
        start = LocalDateTime.parse("2024-04-02T13:00:00"),
        end = LocalDateTime.parse("2024-04-02T21:00:00"),
        checkIn = LocalDateTime.parse("2024-04-02T13:00:00"),
        checkOut = LocalDateTime.parse("2024-04-02T20:45:00"),
        workDuration = 7.75f,
        shiftType = ShiftType.entries.random(),
        user = exampleUser
    ),
    Shift(
        id = 3,
        start = LocalDateTime.parse("2024-04-03T22:00:00"),
        end = LocalDateTime.parse("2024-04-04T06:00:00"),
        checkIn = LocalDateTime.parse("2024-04-03T22:10:00"),
        checkOut = LocalDateTime.parse("2024-04-04T05:50:00"),
        workDuration = 7.67f,
        shiftType = ShiftType.entries.random(),
        user = exampleUser
    ),
    Shift(
        id = 4,
        start = LocalDateTime.parse("2024-04-04T09:00:00"),
        end = LocalDateTime.parse("2024-04-04T17:00:00"),
        checkIn = null,
        checkOut = null,
        workDuration = 0f,
        shiftType = ShiftType.entries.random(),
        user = exampleUser
    ),
    Shift(
        id = 5,
        start = LocalDateTime.parse("2024-04-05T08:00:00"),
        end = LocalDateTime.parse("2024-04-05T16:00:00"),
        checkIn = LocalDateTime.parse("2024-04-05T08:00:00"),
        checkOut = LocalDateTime.parse("2024-04-05T16:00:00"),
        workDuration = 8.0f,
        shiftType = ShiftType.entries.random(),
        user = exampleUser
    )
)

val fakeCheckInfos = listOf(
    CheckInfo(
        type = CheckType.CheckIn,
        time = LocalDateTime.parse("2024-04-01T08:59:00"),
        status = "On time"
    ),
    CheckInfo(
        type = CheckType.CheckOut,
        time = LocalDateTime.parse("2024-04-01T17:05:00"),
        status = "On time"
    ),
    CheckInfo(
        type = CheckType.CheckIn,
        time = LocalDateTime.parse("2024-04-02T09:15:00"),
        status = "Late"
    ),
    CheckInfo(
        type = CheckType.CheckOut,
        time = LocalDateTime.parse("2024-04-02T16:45:00"),
        status = "Left early"
    ),
    CheckInfo(
        type = CheckType.CheckIn,
        time = LocalDateTime.parse("2024-04-03T08:50:00"),
        status = "Early"
    )
)

