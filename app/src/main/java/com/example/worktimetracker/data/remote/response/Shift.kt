package com.example.worktimetracker.data.remote.response

import java.time.LocalDateTime


data class Shift(
    val id: Int,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val checkIn: LocalDateTime? = null,
    val checkOut: LocalDateTime? = null,
    val workDuration: Float,
    val shiftType: ShiftType,
    val user: User? = null,
)

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