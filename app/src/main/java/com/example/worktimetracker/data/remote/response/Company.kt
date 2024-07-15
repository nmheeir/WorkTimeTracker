package com.example.worktimetracker.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val id: Int,
    val companyName: String,
    val users: List<User>,
    val teams: List<Team>
) : Parcelable
