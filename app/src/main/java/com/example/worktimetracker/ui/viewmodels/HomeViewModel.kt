package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.remote.response.CheckInfo
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.use_case.shift.ShiftUseCase
import com.example.worktimetracker.domain.use_case.user.UserUseCase
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
    val todayShifts = MutableStateFlow<List<Shift>>(emptyList())
    val currentWeekDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val checkInfos = MutableStateFlow<List<CheckInfo>>(emptyList())

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
                    todayShifts.value = this.data.data ?: emptyList()
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
//                start = LocalDateTime.now().with(LocalTime.MIN),
//                end = LocalDateTime.now().with(LocalTime.MAX)
            ).suspendOnSuccess {
                checkInfos.value = this.data.data?.checkInfos ?: emptyList()
                Timber.d(this.data.data.toString())
            }.suspendOnException {
                Timber.d(this.message())
            }
        }
    }
}