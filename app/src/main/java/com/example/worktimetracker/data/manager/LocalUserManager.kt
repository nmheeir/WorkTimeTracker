package com.example.worktimetracker.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class LocalUserManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val appContext = context.applicationContext

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("accessToken")
    }

    val accessToken : Flow<String?>
        get() = appContext.dataStore.data.map {
            it[ACCESS_TOKEN] ?: ""
        }

    suspend fun saveAccessToken(accessToken: String) {
        appContext.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun clear() {
        appContext.dataStore.edit {
            it.clear()
        }
    }

    suspend fun getAccessTokenAsString() : String? {
        return appContext.dataStore.data.map {
            it[ACCESS_TOKEN]
        }.firstOrNull()
    }
}