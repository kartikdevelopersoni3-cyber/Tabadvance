package com.roohi.app.core.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "roohi_settings")

@Singleton
class SettingsManager @Inject constructor(private val context: Context) {

    // Settings / Configuration loader implementation via DataStore
    companion object {
        val IS_FIRST_RUN = booleanPreferencesKey("is_first_run")
        val IS_OFFLINE_MODE = booleanPreferencesKey("is_offline_mode")
    }

    val isFirstRun: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_FIRST_RUN] ?: true
    }

    val isOfflineMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_OFFLINE_MODE] ?: false
    }

    suspend fun setFirstRunCompleted() {
        context.dataStore.edit { preferences ->
            preferences[IS_FIRST_RUN] = false
        }
    }

    suspend fun setOfflineMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_OFFLINE_MODE] = enabled
        }
    }
}
