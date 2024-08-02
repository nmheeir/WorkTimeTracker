package com.example.worktimetracker.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shift(
    @SerializedName("id") val id: Int,
    @SerializedName("start") val start: Long,
    @SerializedName("end") val end: Long,
    @SerializedName("userId") val userId: Int,
    @SerializedName("day") val day: Int,
    @SerializedName("month") val month: Int,
    @SerializedName("year") val year: Int,
    @SerializedName("user") val user: User? = null
) : Parcelable

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