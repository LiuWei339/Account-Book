package com.wl.accountbook.ui.record.calculator

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val number1HasDecimal: Boolean = false,
    val number2HasDecimal: Boolean = false,
    val number1Decimal: String = "",
    val number2Decimal: String = "",
    val operation: CalculatorOperation? = null
)
