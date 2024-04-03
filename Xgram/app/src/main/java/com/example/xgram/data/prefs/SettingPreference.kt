package com.example.xgram.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class SettingPreference(private val dataStore: DataStore<Preferences>) {

    private val DARKMODE_KEY = intPreferencesKey("theme_setting")

    fun getDarkMode(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[DARKMODE_KEY] ?: 0
        }
    }

    suspend fun saveDarkMode(currentTheme: Int){
        dataStore.edit { preferences ->
            preferences[DARKMODE_KEY] = currentTheme
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreference {
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}