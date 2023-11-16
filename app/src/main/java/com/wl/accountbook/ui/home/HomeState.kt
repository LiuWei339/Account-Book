package com.wl.accountbook.ui.home

import com.wl.domain.model.MoneyRecordAndType
import java.util.Date

data class HomeState(
    val titleTime: String = "",
    val totalIncome: Long = 0,
    val totalExpenses: Long = 0,
    val recordsByDay: List<Pair<Date, List<MoneyRecordAndType>>> = emptyList()
)
