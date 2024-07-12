package com.example.worktimetracker.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.worktimetracker.domain.manager.LocalUserManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class LocalUserManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalUserManager {
    private val appContext = context.applicationContext

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val APP_ENTRY = booleanPreferencesKey("app_entry")
    }

    val accessToken : Flow<String?>
        get() = appContext.dataStore.data.map {
            it[ACCESS_TOKEN] ?: ""
        }

    val appEntry: Flow<Boolean>
        get() = appContext.dataStore.data.map {
            it[APP_ENTRY] ?: false
        }

    override suspend fun saveAppEntry() {
        appContext.dataStore.edit {
            it[APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return appContext.dataStore.data.map {
            it[APP_ENTRY] ?: false
        }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        appContext.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    override fun readAccessToken(): Flow<String> {
        return appContext.dataStore.data.map {
            it[ACCESS_TOKEN] ?: ""
        }
    }

    suspend fun clear() {
        appContext.dataStore.edit {
            it.clear()
        }
    }

}