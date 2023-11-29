package com.wl.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveCurrency(code: String)

    fun readCurrency(): Flow<String>

    fun isInitialized(): Flow<Boolean>
    suspend fun setInitialized()

    fun savedLanguage(): Flow<String>
    suspend fun saveLanguage(language: String)
}