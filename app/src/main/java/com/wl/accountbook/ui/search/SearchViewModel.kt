package com.wl.accountbook.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recordRepo: RecordRepo
): ViewModel() {

    private val _stateFlow = MutableStateFlow(
        SearchState(
            searchText = "",
            records = emptyList()
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    fun onAction(action: SearchAction) {
        when(action) {
            SearchAction.Search, SearchAction.START -> {
                search(_stateFlow.value.searchText)
            }
            is SearchAction.ChangeSearchText -> {
                _stateFlow.value = _stateFlow.value.copy(searchText = action.text)
            }
        }
    }

    private fun search(text: String) {
        if (text.isEmpty()) {
            _stateFlow.value = SearchState(
                searchText = "",
                records = emptyList()
            )
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val list = recordRepo.searchRecordAndTypes(text)
            _stateFlow.emit(
                _stateFlow.value.copy(
                    searchText = text,
                    records = list
                )
            )
        }
    }
}