package com.example.worktimetracker.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LocalUserManager(
    private val context: Context
) {

}

private object PreferenceKeys {
    val IS_LOGIN = booleanPreferencesKey("isLogin")
}