package com.example.worktimetracker.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayCheck(
    val allowanced: Double,
    val createAt: String,
    val end: String,
    val id: Int,
    val nightWork: Double,
    val normalWork: Double,
    val overtimeWork: Double,
    val start: String,
    val totalIncome: Double,
    val totalWorkTime: Double,
    val userId: Int
) : Parcelable