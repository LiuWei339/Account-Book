package com.wl.domain.usecases.currency

import com.wl.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCurrency @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    operator fun invoke(): Flow<String> {
        return localUserManager.readCurrency()
    }
}