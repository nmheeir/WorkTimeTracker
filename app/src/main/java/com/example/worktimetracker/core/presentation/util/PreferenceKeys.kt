package com.example.worktimetracker.core.presentation.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

val TokenKey = stringPreferencesKey("token")
val AppThemeKey = stringPreferencesKey("app_theme")
val DeviceTokenKey = stringPreferencesKey("device_token")
val UsernameKey = stringPreferencesKey("username")
val AppEntryKey = booleanPreferencesKey("app_entry")