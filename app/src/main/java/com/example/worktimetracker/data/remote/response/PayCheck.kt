package com.example.worktimetracker.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayCheck(
    val allowanced: Double,
    val createAt: Long,
    val end: Long,
    val id: Int,
    val nightWork: Double,
    val normalWork: Double,
    val overtimeWork: Double,
    val start: Long,
    val totalIncome: Double,
    val totalWorkTime: Double,
    val userId: Int
) : Parcelable