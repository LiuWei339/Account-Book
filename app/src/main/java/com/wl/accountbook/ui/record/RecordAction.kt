package com.wl.accountbook.ui.record

import com.wl.accountbook.ui.record.calculator.CalculatorAction

sealed class RecordAction{
    // tabs
    sealed class PressTab(val tabIndex: Int): RecordAction()
    object PressExpenseTab: PressTab(0)
    object PressIncomeTab: PressTab(1)

    data class SelectType(val index: Int): RecordAction()
    object ClickDate: RecordAction()
    data class SelectDate(val timeStamp: Long): RecordAction()
    data class ChangeNote(val note: String): RecordAction()
    abstract class RecordCalculateAction: RecordAction()
}
