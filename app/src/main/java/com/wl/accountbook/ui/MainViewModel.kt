package com.wl.accountbook.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.accountbook.data.PresetRecordTypes
import com.wl.common.util.LogUtil
import com.wl.domain.manager.LocalUserManager
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localUserManager: LocalUserManager,
    private val recordRepo: RecordRepo,
    private val presetRecordTypes: PresetRecordTypes
): ViewModel() {

    var showSplash by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                initPresetRecordTypes()
            }
            delay(300)
            showSplash = false
        }
    }

    /**
     * preset record types, id of these types should start from 1000
     */
    private suspend fun initPresetRecordTypes() {
        val currentLanguage = Locale.getDefault().language
        val isLanguageUnChanged = localUserManager.savedLanguage().map { it == currentLanguage }.first()
        if (isLanguageUnChanged) return
        LogUtil.i("MainViewModel", "init or update preset record types")
        recordRepo.addOrUpdateTypes(presetRecordTypes())
        localUserManager.saveLanguage(currentLanguage)
    }
}