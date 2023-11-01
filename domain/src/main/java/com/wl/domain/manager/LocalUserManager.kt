package com.wl.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveCurrency(code: String)

    fun readCurrency(): Flow<String>
}