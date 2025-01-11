package com.example.worktimetracker.core.data.network

import com.example.worktimetracker.core.domain.util.Error
import com.example.worktimetracker.core.domain.util.NetworkError
import com.google.gson.JsonParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun handleException(throwable: Throwable): Error {
    return when (throwable) {
        is ConnectException -> {
            NetworkError.NO_INTERNET
        }
        is SocketTimeoutException -> {
            NetworkError.REQUEST_TIMEOUT // Thời gian kết nối hết hạn
        }

        is UnknownHostException -> {
            NetworkError.NO_INTERNET // Không thể xác định hostname
        }

        is JsonParseException -> {
            NetworkError.SERIALIZATION // Lỗi phân tích cú pháp JSON
        }

        is IllegalArgumentException -> {
            NetworkError.UNKNOWN
        }

        else -> {
            NetworkError.UNKNOWN
        }
    }
}