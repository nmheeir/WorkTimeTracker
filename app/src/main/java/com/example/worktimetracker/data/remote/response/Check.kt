package com.example.worktimetracker.data.remote.response

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Check(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("checkType") val checkType: Int,
    @SerializedName("checkTime") val checkTime: Long,
    @SerializedName("user") val user: User? = null
) {
    fun checkType(): String {
        return when (checkType) {
            0 -> CheckType.CHECKIN.displayStatus()
            1 -> CheckType.CHECKOUT.displayStatus()
            else -> "UNKNOWN"
        }
    }

    fun getDateForCheck() : String {
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        val date = Date(checkTime)
        return dateFormat.format(date)
    }

    fun getHour() : String {
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = Date(checkTime)
        return dateFormat.format(date)
    }
}

enum class CheckType {
    CHECKIN,
    CHECKOUT;

    fun displayStatus(): String {
        return this.name.lowercase().replaceFirstChar {
            it.uppercase()
        }
    }

}
