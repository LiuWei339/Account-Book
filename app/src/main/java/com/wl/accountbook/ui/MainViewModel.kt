package com.wl.accountbook.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.domain.manager.LocalUserManager
import com.wl.domain.model.MoneyRecordType
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    localUserManager: LocalUserManager,
    recordRepo: RecordRepo // TODO delete
): ViewModel() {

    var showSplash by mutableStateOf(true)
        private set

    init {
        Log.d("test1", "main viewmodel init")
        viewModelScope.launch {

            Log.d("test1", "viewModelScope launch ")
            Log.d("test1", "current state: ${localUserManager.isInitialized().first()}")
            Log.d("test1", "viewModelScope launch1 ")

            if (!localUserManager.isInitialized().first()) {
                recordRepo.addOrUpdateType(
                    MoneyRecordType(
                        1,
                        "Food",
                        "🎮",
                        true,
                        true
                    )
                )

                recordRepo.addOrUpdateType(
                    MoneyRecordType(
                        2,
                        "Work",
                        "🖥️",
                        false,
                        true
                    )
                )

                Log.d("test1", "init db")

                localUserManager.setInitialized()
            }

            delay(300)
            showSplash = false
            Log.d("test1", "showSplash change to $showSplash")
        }
    }
}