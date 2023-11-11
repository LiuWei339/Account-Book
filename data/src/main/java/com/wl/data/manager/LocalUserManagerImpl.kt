package com.wl.data.manager

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wl.data.util.Constants
import com.wl.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserManagerImpl @Inject constructor(
    private val application: Application
): LocalUserManager {

    override suspend fun saveCurrency(code: String) {
        application.dataStore.edit { settings ->
            settings[PreferenceKeys.CURRENCY] = code
        }
    }

    override fun readCurrency(): Flow<String> = application.dataStore.data
        .map { preferences ->
            preferences[PreferenceKeys.CURRENCY] ?: ""
        }

    override fun isInitialized(): Flow<Boolean> = application.dataStore.data
        .map { preferences ->
            preferences[PreferenceKeys.INITIALIZED] ?: false
        }

    override suspend fun setInitialized() {
        application.dataStore.edit { settings ->
            settings[PreferenceKeys.INITIALIZED] = true
        }
    }

}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.USER_SETTINGS)

private object PreferenceKeys {
    val CURRENCY = stringPreferencesKey(Constants.CURRENCY_KEY)
    val INITIALIZED = booleanPreferencesKey(Constants.INITIALIZED_KEY)
}