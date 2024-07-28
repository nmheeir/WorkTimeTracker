package com.example.worktimetracker.ui.util

fun validateEmail(email: String): Boolean {
    val regex = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    return email.matches(regex.toRegex())
}

fun String.isValidUsername() : Boolean {
    val regex = "^(?!.*[A-Z])(?!^\\d)(?!.*[!@#\$%^&*(),.?\":{}|<>])(?!.*[\\u00C0-\\u024F\\u1E00-\\u1EFF]).{6,}\$"
    return this.matches(regex.toRegex())
}

fun validatePassword(password: String): Boolean {
    val regex = "^\\S{3,}$"
    return password.matches(regex.toRegex())
}
