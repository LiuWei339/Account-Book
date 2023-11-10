package com.wl.accountbook.ui.record

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wl.accountbook.ui.record.calculator.CalculatorAction
import com.wl.accountbook.ui.record.calculator.CalculatorOperation
import com.wl.accountbook.ui.record.calculator.CalculatorState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class RecordViewModel @Inject constructor() : ViewModel() {

    var calState by mutableStateOf(CalculatorState())
        private set

    fun onCalcAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Delete -> delete()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> calculate()
            is CalculatorAction.Decimal -> enterDecimal()
        }
    }

    private fun enterDecimal() {
        if (calState.operation == null) {
            if (!calState.number1HasDecimal) {
                calState = calState.copy(
                    number1HasDecimal = true
                )
            }
        } else {
            if (!calState.number2HasDecimal) {
                calState = calState.copy(
                    number2HasDecimal = true
                )
            }
        }
    }

    private fun calculate() {
        if (calState.operation == null) return

        var calDecimal1 = 0L
        if (calState.number1HasDecimal) {
            var number1Decimal = calState.number1Decimal
            number1Decimal += "0".repeat(MAX_DECIMAL_LENGTH - number1Decimal.length)
            calDecimal1 = number1Decimal.toLong()
        }
        val num1 =
            (calState.number1.ifEmpty { "0" }).toLong() * multiplier + calDecimal1

        var calDecimal2 = 0L
        if (calState.number2HasDecimal) {
            var number2Decimal = calState.number2Decimal
            number2Decimal += "0".repeat(MAX_DECIMAL_LENGTH - number2Decimal.length)
            calDecimal2 = number2Decimal.toLong()
        }
        val num2 =
            (calState.number2.ifEmpty { "0" }).toLong() * multiplier + calDecimal2

        val res = when (calState.operation) {
            CalculatorOperation.Add -> num1 + num2
            CalculatorOperation.Subtract -> num1 - num2
            else -> return
        }
        val intRes = res / multiplier
        val decimalRes = res - intRes * multiplier
        calState = CalculatorState(
            number1 = (res / multiplier).toString().take(MAX_NUMBER_LENGTH),
            number1HasDecimal = decimalRes > 0,
            number1Decimal = if (decimalRes > 0) {
                var str = decimalRes.toString()
                ("0".repeat(MAX_DECIMAL_LENGTH - str.length) + str).dropLastWhile { it == '0' }
            } else "",
        )
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (calState.number2.isNotEmpty() || calState.number2HasDecimal) {
            calculate()
        } else if (calState.number1.isNotEmpty() || calState.number1HasDecimal) {
            calState = calState.copy(
                operation = operation
            )
        }
    }

    private fun enterNumber(digit: Int) {
        if (calState.operation == null) {
            if (calState.number1HasDecimal) {
                if (calState.number1Decimal.length < MAX_DECIMAL_LENGTH) {
                    calState = calState.copy(
                        number1Decimal = calState.number1Decimal + digit
                    )
                }
            } else {
                if (calState.number1.isEmpty() && digit == 0) {
                    return
                }
                if (calState.number1.length < MAX_NUMBER_LENGTH) {
                    calState = calState.copy(
                        number1 = calState.number1 + digit
                    )
                }
            }
        } else {
            if (calState.number2HasDecimal) {
                if (calState.number2Decimal.length < MAX_DECIMAL_LENGTH) {
                    calState = calState.copy(
                        number2Decimal = calState.number2Decimal + digit
                    )
                }
            } else {
                if (calState.number2.isEmpty() && digit == 0) {
                    return
                }
                if (calState.number2.length < MAX_NUMBER_LENGTH) {
                    calState = calState.copy(
                        number2 = calState.number2 + digit
                    )
                }
            }
        }
    }

    private fun delete() {
        when {
            calState.number2Decimal.isNotEmpty() -> calState = calState.copy(
                number2Decimal = calState.number2Decimal.dropLast(1)
            )

            calState.number2HasDecimal -> calState = calState.copy(
                number2HasDecimal = false
            )

            calState.number2.isNotEmpty() -> calState = calState.copy(
                number2 = calState.number2.dropLast(1)
            )

            calState.operation != null -> calState.copy(
                operation = null
            )

            calState.number1Decimal.isNotEmpty() -> calState = calState.copy(
                number1Decimal = calState.number1Decimal.dropLast(1)
            )

            calState.number1HasDecimal -> calState = calState.copy(
                number1HasDecimal = false
            )

            calState.number1.isNotEmpty() -> calState = calState.copy(
                number1 = calState.number1.dropLast(1)
            )
        }
    }

    companion object {
        private const val MAX_DECIMAL_LENGTH = 2
        private const val MAX_NUMBER_LENGTH = 9
        private val multiplier = 10.0.pow(MAX_DECIMAL_LENGTH).toLong()
    }
}