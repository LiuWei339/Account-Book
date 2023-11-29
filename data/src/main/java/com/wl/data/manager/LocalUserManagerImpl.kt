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
        save(PreferenceKeys.CURRENCY, code)
    }

    override fun readCurrency(): Flow<String> = read(PreferenceKeys.CURRENCY, "")

    override fun isInitialized(): Flow<Boolean> = read(PreferenceKeys.INITIALIZED, false)

    override suspend fun setInitialized() {
        save(PreferenceKeys.INITIALIZED, true)
    }

    override fun savedLanguage(): Flow<String> {
        return read(PreferenceKeys.LANGUAGE, "")
    }

    override suspend fun saveLanguage(language: String) {
        save(PreferenceKeys.LANGUAGE, language)
    }

    private suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        application.dataStore.edit { settings ->
            settings[key] = value
        }
    }

    private fun <T> read(key: Preferences.Key<T>, default: T): Flow<T> {
        return application.dataStore.data
            .map { preferences ->
                preferences[key] ?: default
            }
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.USER_SETTINGS)

private object PreferenceKeys {
    val CURRENCY = stringPreferencesKey(Constants.CURRENCY_KEY)
    val INITIALIZED = booleanPreferencesKey(Constants.INITIALIZED_KEY)
    val LANGUAGE = stringPreferencesKey(Constants.LANGUAGE_KEY)
}