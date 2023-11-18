package com.wl.accountbook.ui.home

import android.util.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.common.util.endOfTheMonth
import com.wl.common.util.startOfTheMonth
import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recordRepo: RecordRepo
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(HomeState(
        date = Date() // TODO
    ))
    val stateFlow = _stateFlow.asStateFlow()

    private var records: List<MoneyRecord>? = null
    private var typeMap: Map<Int, MoneyRecordType>? = null
    private var getRecordsJob: Job

    init {
        getRecordsJob = getRecordsOfTheMonth(Date().time)
        getRecordTypes()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.ClickSearch -> {}
            is HomeAction.SelectDate -> {
                _stateFlow.value = stateFlow.value.copy(date = Date(action.timeStamp))
                getRecordsJob.cancel()
                getRecordsJob = getRecordsOfTheMonth(action.timeStamp)
            }
            is HomeAction.PressRecord -> {
                val record = action.record
                // go to record details  TODO

            }
        }
    }

    private fun getRecordsOfTheMonth(time: Long): Job {
        val start = time.startOfTheMonth()
        val end = time.endOfTheMonth()

        return recordRepo.getSortedRecords(start, end).onEach { list ->
            records = list
            freshRecords()
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun getRecordTypes() {
        recordRepo.getRecordTypes().onEach {
            val map = ArrayMap<Int, MoneyRecordType>()
            it.forEach { type ->
                map[type.id] = type
            }
            typeMap = map
            freshRecords()
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private suspend fun freshRecords() {
        if (records == null || typeMap == null) return

        val recordsByDay = records!!.map { record ->
            MoneyRecordAndType(
                record, typeMap!![record.typeId]!!
            )
        }
            .groupBy { it.recordTime }
            .toList()
            .map { Pair(Date(it.first), it.second) }

        val totalIncome = records!!.filter { typeMap!![it.typeId]?.isExpenses == false }
            .sumOf { it.amount }
        val totalExpenses = records!!.filter { typeMap!![it.typeId]?.isExpenses == true }
            .sumOf { it.amount }

        _stateFlow.emit(
            stateFlow.value.copy(
                totalIncome = totalIncome,
                totalExpenses = totalExpenses,
                recordsByDay = recordsByDay
            )
        )
    }
}