package com.wl.accountbook.ui.home

import android.app.DatePickerDialog
import android.util.ArrayMap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.common.util.endOfTheMonth
import com.wl.common.util.monthYearFormat
import com.wl.common.util.startOfTheMonth
import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recordRepo: RecordRepo
) : ViewModel() {

    var state by mutableStateOf(
        HomeState(
            titleTime = Date().monthYearFormat()
        )
    )

    private var records: List<MoneyRecord> = emptyList()
    private var typeMap: Map<Int, MoneyRecordType> = emptyMap()

    init {
        getRecordsOfTheMonth(Date().time)

        recordRepo.getRecordTypes().onEach {
            val map = ArrayMap<Int, MoneyRecordType>()
            it.forEach { type ->
                map[type.id] = type
            }
            typeMap = map
            freshRecords()
        }.launchIn(viewModelScope)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.ClickSearch -> {}
            HomeAction.ClickDate -> {}
            is HomeAction.PressRecord -> {
                val record = action.record
                // go to record details  TODO

            }
        }
    }

    private fun getRecordsOfTheMonth(time: Long) {
        val start = time.startOfTheMonth()
        val end = time.endOfTheMonth()

        recordRepo.getSortedRecords(start, end).onEach { list ->
            records = list
            freshRecords()
        }.launchIn(viewModelScope)
    }

    private fun freshRecords() {
        if (records.isEmpty() || typeMap.isEmpty()) return
        val recordsByDay = records.map { record ->
            MoneyRecordAndType(
                record, typeMap[record.typeId]!!
            )
        }
            .groupBy { it.recordTime }
            .toList()
            .map { Pair(Date(it.first), it.second) }

        val totalIncome = records.filter { typeMap[it.typeId]?.isExpenses == false }
            .sumOf { it.amount }
        val totalExpenses = records.filter { typeMap[it.typeId]?.isExpenses == true }
            .sumOf { it.amount }

        state = state.copy(
            totalIncome = totalIncome,
            totalExpenses = totalExpenses,
            recordsByDay = recordsByDay
        )
    }
}