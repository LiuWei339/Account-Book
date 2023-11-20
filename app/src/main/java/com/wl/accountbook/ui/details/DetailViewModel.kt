package com.wl.accountbook.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recordRepo: RecordRepo
): ViewModel() {

    private val detailArgs = DetailArgs(savedStateHandle)

    var state by mutableStateOf<MoneyRecordAndType?>(null)

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            state = recordRepo.getRecordAndType(detailArgs.recordCreateTime)
        }
    }

    fun delete() {
        viewModelScope.launch(context = Dispatchers.IO) {
            recordRepo.deleteRecord(detailArgs.recordCreateTime)
        }
    }
}