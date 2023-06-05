package com.zeroone.recyclo.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class SessionPreference private constructor(private val dataStore: DataStore<Preferences>){

    fun getUser(): kotlinx.coroutines.flow.Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[NAME] ?: "",
                preferences[TOKEN] ?: "",
                (preferences[STATE] ?: false) as Boolean

            )

        }
    }
    fun getToken(): kotlinx.coroutines.flow.Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }

    suspend fun setToken(token:String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    suspend fun clearToken() {
        dataStore.edit{preference ->
            preference.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SessionPreference? = null

        private val TOKEN = stringPreferencesKey("token")
        private val NAME = stringPreferencesKey("name")
        private val STATE = stringPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): SessionPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SessionPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}