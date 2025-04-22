package com.example.worktimetracker.data.remote.enums

enum class EmployeeType(val value: Int) {
    Pernament(0),
    Fulltime(1),
    Parttime(2);

    companion object {
        fun fromInt(value: Int): EmployeeType? {
            return entries.find { it.value == value }
        }

        fun fromIntToName(value: Int): String? {
            return entries.find { it.value == value }?.name
                ?.replace("_", " ")
                ?.split(" ")
                ?.joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
        }
    }
}