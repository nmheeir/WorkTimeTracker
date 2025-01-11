package com.example.worktimetracker.data.remote.response

data class TotalWorkTime (
     val id : Int,
    val username : String,
    val totalHours :Double,
     val normalHours : Double,
     val nightHours : Double,
    val overtimeHours : Double,
)