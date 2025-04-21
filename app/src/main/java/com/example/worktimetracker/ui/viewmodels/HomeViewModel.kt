package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import timber.log.Timber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val token = context.dataStore.get(TokenKey, "")

    val isLoading = MutableStateFlow(false)

    val user = MutableStateFlow<User?>(null)
    val currentWeekDates = mutableStateMapOf<String, LocalDate>()

    init {
        viewModelScope.launch {
            fetchUserProfile()
            currentWeekDates.putAll(getCurrentWeekList())
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

    fun getCurrentWeekList(): List<Pair<String, LocalDate>> {
        val today = LocalDate.now()
        val monday = today.with(DayOfWeek.MONDAY)

        return (0..6).map { i ->
            val date = monday.plusDays(i.toLong())
            val dayName = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("vi"))
            dayName to date
        }
    }

}