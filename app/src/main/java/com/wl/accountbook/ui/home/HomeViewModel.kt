package com.wl.accountbook.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.common.util.LogUtil
import com.wl.common.util.dayWithWeekFormat
import com.wl.common.util.endOfTheMonth
import com.wl.common.util.monthYearFormat
import com.wl.common.util.startOfTheMonth
import com.wl.common.util.toDate
import com.wl.domain.model.MoneyRecord
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recordRepo: RecordRepo
): ViewModel() {

    var state by mutableStateOf(HomeState(
        titleTime = Date().monthYearFormat()
    ))

    init {
        getRecordsOfTheMonth(Date().time).onEach {
            LogUtil.d("test1", "recordsByDay count: ${it.size}")
            state = state.copy(
                recordsByDay = it
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: HomeAction) {
        when(action) {
            HomeAction.ClickSearch -> {}
            HomeAction.ClickDate -> {}
            is HomeAction.PressRecord -> {
                val record = action.record
            }
        }
    }

    private fun getRecordsOfTheMonth(time: Long): Flow<List<Pair<Date, List<MoneyRecord>>>> {
        val start = time.startOfTheMonth()
        val end = time.endOfTheMonth()
        return recordRepo.getRecords(start, end).map { list ->
            list.groupBy { it.recordTime }.toList().map { Pair(Date(it.first), it.second) }
        }
    }
}