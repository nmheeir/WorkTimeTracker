package com.example.worktimetracker.data.local

import androidx.room.TypeConverter
import com.example.worktimetracker.data.remote.enums.CheckType
import com.example.worktimetracker.data.remote.enums.EmployeeType
import com.example.worktimetracker.data.remote.enums.NotificationType
import com.example.worktimetracker.data.remote.enums.Role
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun localDateTimeToString(value: LocalDateTime): String {
        return value.toString()
    }

    @TypeConverter
    fun stringToLocalDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }

    @TypeConverter
    fun notificationTypeToString(value: NotificationType?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToNotificationType(value: String?): NotificationType? {
        return value?.let { NotificationType.valueOf(it) }
    }

    @TypeConverter
    fun checkTypeToString(value: CheckType?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToCheckType(value: String?): CheckType? {
        return value?.let { CheckType.valueOf(it) }
    }

    @TypeConverter
    fun employeeTypeToString(value: EmployeeType?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToEmployeeType(value: String?): EmployeeType? {
        return value?.let { EmployeeType.valueOf(it) }
    }

    @TypeConverter
    fun roleToString(value: Role?): String? {
        return value?.name
    }

    @TypeConverter
    fun stringToRole(value: String?): Role? {
        return value?.let { Role.valueOf(it) }
    }
}