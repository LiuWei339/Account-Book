package com.wl.accountbook.ui.stats

sealed class StatsAction {
    data class SelectYear(val timeStamp: Long): StatsAction()
    data class SelectMonth(val timeStamp: Long): StatsAction()
    sealed class PressTab(val tabIndex: Int): StatsAction()
    object PressExpenseTab: PressTab(0)
    object PressIncomeTab: PressTab(1)
}