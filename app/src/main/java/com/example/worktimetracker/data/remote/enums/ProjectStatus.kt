package com.example.worktimetracker.data.remote.enums

enum class ProjectStatus(val title: String) {
    NotStarted("Not Started"),
    InProgress("In Progress"),
    Completed("Completed"),
    OnHold("On Hold"),
    Cancelled("Cancelled")
}