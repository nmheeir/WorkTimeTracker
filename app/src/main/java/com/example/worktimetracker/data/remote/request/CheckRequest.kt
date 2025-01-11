package com.example.worktimetracker.data.remote.request


data class CheckRequest (
    val userId : Int = 0,
    val checkType: Int = 0,
    val shiftId: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)