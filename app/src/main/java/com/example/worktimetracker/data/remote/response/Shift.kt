package com.example.worktimetracker.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Shift(
    val id: Int,
    val start: Long,
    val end: Long,
    val shiftType: ShiftType,
    val userId: Int,
    val user: User?
) : Parcelable

enum class ShiftType {
    Normal,
    Overtime,
    NightShift
}