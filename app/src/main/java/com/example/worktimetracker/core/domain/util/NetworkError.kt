package com.example.worktimetracker.core.domain.util

enum class NetworkError(var message: String): Error {
    REQUEST_TIMEOUT("Request timed out."),
    TOO_MANY_REQUESTS("Too many requests. Please try again later."),
    NO_INTERNET("No internet connection."),
    SERVER_ERROR("Server encountered an error."),
    SERIALIZATION("Serialization error occurred."),
    UNKNOWN("An unknown error occurred."),
    CUSTOM_ERROR("") {
        override fun withMessage(customMessage: String): NetworkError {
            return CUSTOM_ERROR.also { it.message = customMessage }
        }
    };

    open fun withMessage(customMessage: String): NetworkError {
        this.message = customMessage
        return this
    }


    override fun showMessage(): String = message
}