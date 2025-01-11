package com.example.worktimetracker.data.remote.response

import java.time.LocalDateTime


data class Shift(
    val id : Int,
    val userId: Int,
    val start: String,
    val end: String,
    val checkIn: String,
    val checkOut: String,
    val workDuration: Float,
    val shiftType: Int,
    val checkRecord: CheckRecord? = null
) {
    val shiftTypeEnum: ShiftType
        get() = ShiftType.entries.toTypedArray().getOrElse(shiftType) { ShiftType.Normal }
}

enum class ShiftType {
    Normal,
    Overtime,
    NightShift;

    companion object {
        fun namesToList(): List<String> {
            return ShiftType.entries.map {
                it.name
            }
        }
    }
}