package com.wl.accountbook.ui.stats

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.accountbook.ui.stats.components.SingleTrendStat
import com.wl.accountbook.ui.stats.components.TypeStats
import com.wl.common.util.endOfTheDay
import com.wl.common.util.endOfTheMonth
import com.wl.common.util.endOfTheYear
import com.wl.common.util.startOfTheDay
import com.wl.common.util.startOfTheMonth
import com.wl.common.util.startOfTheYear
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val recordRepo: RecordRepo
): ViewModel() {
    var state by mutableStateOf(StatsState(
        timeStamp = System.currentTimeMillis().startOfTheMonth(),
        isShowMonthStats = true,
        tabIndex = 0,
        trendStats = emptyList(),
        statsByType = emptyList()
    ))
        private set

    private var isExpenseStats = true
    private var startTime = System.currentTimeMillis().startOfTheMonth()
    private var endTime = System.currentTimeMillis().endOfTheMonth()
    private var getStatsJob: Job? = null

    init {
        refreshState()
    }

    fun onAction(action: StatsAction) {
        when(action) {
            is StatsAction.SelectYear -> {
                if (startTime == action.timeStamp.startOfTheYear()
                    && endTime == action.timeStamp.endOfTheYear()) return
                startTime = action.timeStamp.startOfTheYear()
                endTime = action.timeStamp.endOfTheYear()
                state = state.copy(
                    timeStamp = startTime,
                    isShowMonthStats = false
                )
                refreshState()
            }
            is StatsAction.SelectMonth -> {
                if (startTime == action.timeStamp.startOfTheMonth()
                    && endTime == action.timeStamp.endOfTheMonth()) return
                startTime = action.timeStamp.startOfTheMonth()
                endTime = action.timeStamp.endOfTheMonth()
                state = state.copy(
                    timeStamp = startTime,
                    isShowMonthStats = true
                )
                refreshState()
            }
            StatsAction.PressExpenseTab -> {
                if (isExpenseStats) return
                isExpenseStats = true
                state = state.copy(tabIndex = 0)
                refreshState()
            }
            StatsAction.PressIncomeTab -> {
                if (!isExpenseStats) return
                isExpenseStats = false
                state = state.copy(tabIndex = 1)
                refreshState()
            }
        }
    }

    private fun refreshState() {
        getStatsJob?.cancel()
        getStatsJob = viewModelScope.launch(Dispatchers.Default) {
            getStats()
        }
    }

    private suspend fun getStats() {
        val recordAndTypes = withContext(Dispatchers.IO) {
             recordRepo.getRecordAndTypes(startTime, endTime, isExpenseStats)
        }

        var maxAmountOfTrend = 0L
        val isShowMonthStats = withContext(Dispatchers.Main) {
            state.isShowMonthStats
        }
        val trendStats = recordAndTypes
            .run {
                if (isShowMonthStats) {
                    map {
                        Pair(it.recordTime.startOfTheDay(), it.amount)
                    }
                        .toMutableList()
                        .apply {
                            var curTime = startTime
                            while (curTime < endTime) {
                                add(Pair(curTime, 0))
                                curTime = curTime.endOfTheDay() + 1
                            }
                        }
                } else {
                    map {
                        Pair(it.recordTime.startOfTheMonth(), it.amount)
                    }
                        .toMutableList()
                        .apply {
                            var curTime = startTime
                            while (curTime < endTime) {
                                add(Pair(curTime, 0))
                                curTime = curTime.endOfTheMonth() + 1
                            }
                        }
                }
            }
            .asSequence()
            .groupBy { it.first }
            .map { entry ->
                val amount = entry.value.sumOf { it.second }
                if (amount > maxAmountOfTrend) maxAmountOfTrend = amount
                Pair(entry.key, amount)
            }
            .sortedBy { it.first }
            .mapIndexed { index, pair ->
                SingleTrendStat(
                    label = (index + 1).toString(),
                    amount = pair.second,
                    percentOfMax = if (maxAmountOfTrend == 0L) 0f else pair.second.toFloat() / maxAmountOfTrend
                )
            }
            .toList()

        var maxAmountOfType = 0L
        val statsByType = recordAndTypes.map {
            Pair(it.type, it.amount)
        }
            .groupBy { it.first.id }
            .map { entry ->
                val amount = entry.value.sumOf { it.second }
                if (amount > maxAmountOfType) maxAmountOfType = amount
                Pair(entry.value.first().first, amount)
            }
            .sortedBy { it.second }
            .map {
                TypeStats(
                    type = it.first,
                    amount = it.second,
                    percentOfMax = it.second.toFloat() / maxAmountOfType
                )
            }

        Log.i("StatsViewModel", "trendStats size: ${trendStats.size}")
        Log.i("StatsViewModel", "statsByType size: ${statsByType.size}")

        withContext(Dispatchers.Main) {
            state = state.copy(
                trendStats = trendStats,
                statsByType = statsByType
            )
        }
    }
}