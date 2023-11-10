package com.wl.accountbook.ui.record.calculator

import com.wl.accountbook.ui.record.RecordAction

sealed class CalculatorAction: RecordAction.RecordCalculateAction() {
    data class Number(val number: Int): CalculatorAction()
    object Delete: CalculatorAction()
    data class Operation(val operation: CalculatorOperation): CalculatorAction()
    object Calculate: CalculatorAction()
    object Decimal: CalculatorAction()
}