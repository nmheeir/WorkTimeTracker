package com.example.worktimetracker.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    val id: Int,
    val name: String,
    val companyId: Int,
    val company: Company,
    val users: List<User>
) : Parcelable
