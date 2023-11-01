package com.wl.domain.usecases.currency

import com.wl.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveCurrency @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke(code: String) {
        localUserManager.saveCurrency(code)
    }
}