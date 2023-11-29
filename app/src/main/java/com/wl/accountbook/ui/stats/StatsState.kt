package com.wl.accountbook.ui.stats

import com.wl.accountbook.ui.stats.components.SingleTrendStat
import com.wl.accountbook.ui.stats.components.TypeStats
import com.wl.domain.model.MoneyRecordType

data class StatsState(
    val timeStamp: Long,
    val isShowMonthStats: Boolean,
    val tabIndex: Int,
    val trendStats: List<SingleTrendStat>,
    val statsByType: List<TypeStats>
)



