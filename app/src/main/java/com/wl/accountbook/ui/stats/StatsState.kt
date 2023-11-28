package com.wl.accountbook.ui.stats

import com.wl.accountbook.ui.stats.components.SingleTrendStat
import com.wl.domain.model.MoneyRecordType

data class StatsState(
    val timeText: String,
    val tabIndex: Int,
    val trendStats: List<SingleTrendStat>,
    val statsByType: List<TypeStats>
)

data class TypeStats(
    val type: MoneyRecordType,
    val amount: String,
    val percent: String
)

