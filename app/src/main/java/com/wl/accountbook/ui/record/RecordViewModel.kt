package com.wl.accountbook.ui.record

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.accountbook.ui.record.calculator.CalculatorAction
import com.wl.accountbook.ui.record.calculator.CalculatorOperation
import com.wl.accountbook.ui.record.calculator.CalculatorState
import com.wl.common.util.startOfTheDay
import com.wl.data.util.Constants.MAX_DECIMAL_LENGTH
import com.wl.data.util.Constants.MAX_NUMBER_LENGTH
import com.wl.data.util.MoneyUtils
import com.wl.domain.model.MoneyRecord
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepo: RecordRepo
) : ViewModel() {

    var calcState by mutableStateOf(CalculatorState())
        private set

    var recordState by mutableStateOf(RecordState(
        showTime = "",
        tabIndex = 0
    ))
        private set

    init {
        viewModelScope.launch {
            recordState = recordState.copy(
                recordTypes = recordRepo.getExpensesRecordTypes().first()
            )
        }
    }

    private fun setCalculatorState(state: CalculatorState) {
        recordState = recordState.copy(
            isValidRecord = state.operation == null
                    && MoneyUtils.transToCalcMoney(state.number1, state.number1Decimal) > 0
        )
        calcState = state
    }

    fun onRecordAction(action: RecordAction) {
        when(action) {
            is RecordAction.PressTab -> {
                if (recordState.tabIndex == action.tabIndex) return
                if (action is RecordAction.PressExpenseTab) {
                    recordRepo.getExpensesRecordTypes()
                } else {
                    recordRepo.getIncomeRecordTypes()
                }.onEach { recordTypes ->
                    recordState = recordState.copy(
                        tabIndex = action.tabIndex,
                        recordTypes = recordTypes,
                        typeIndexId = -1
                    )
                }.launchIn(viewModelScope)
            }
            is RecordAction.SelectType -> {
                recordState = recordState.copy(
                    typeIndexId = action.index
                )
            }
            is RecordAction.ClickDate -> {
                recordState = recordState.copy(
                    showDateSelector = true  // TODO whether should I use this param, or pop a dialog instead
                )
            }
            is RecordAction.SelectDate -> {
                // TODO use a data select widget
            }
            is RecordAction.ChangeNote -> {
                recordState = recordState.copy(
                    note = action.note
                )
            }
            is RecordAction.RecordCalculateAction -> {
                if (action is CalculatorAction) {
                    onCalcAction(action)
                }
            }
        }
    }

    private fun addRecord() {
        viewModelScope.launch {
            val record = MoneyRecord(
                amount = MoneyUtils.transToCalcMoney(calcState.number1, calcState.number1Decimal),
                type = recordState.recordTypes[recordState.typeIndexId],
                note = recordState.note,
                recordTime = System.currentTimeMillis().startOfTheDay(), // TODO use time selector
                createTime = System.currentTimeMillis()
            )
            recordRepo.addOrUpdateRecord(record)
        }
    }

    // calculation
    private fun onCalcAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Delete -> delete()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> calculate()
            is CalculatorAction.Decimal -> enterDecimal()
        }
    }

    private fun enterDecimal() {
        if (calcState.operation == null) {
            if (!calcState.number1HasDecimal) {
                setCalculatorState(calcState.copy(
                    number1HasDecimal = true
                ))
            }
        } else {
            if (!calcState.number2HasDecimal) {
                setCalculatorState(calcState.copy(
                    number2HasDecimal = true
                ))
            }
        }
    }

    private fun calculate() {
        if (calcState.number2.isNotEmpty() || calcState.number2HasDecimal) {
            doActualCalculate()
            return
        }

        if (calcState.operation != null) {
            setCalculatorState(calcState.copy(operation = null))
        } else {
            addRecord()
        }
    }

    private fun doActualCalculate() {
        val num1 = MoneyUtils.transToCalcMoney(calcState.number1, calcState.number1Decimal)
        val num2 = MoneyUtils.transToCalcMoney(calcState.number2, calcState.number2Decimal)
        val res = when (calcState.operation) {
            CalculatorOperation.Add -> num1 + num2
            CalculatorOperation.Subtract -> num1 - num2
            else -> return
        }
        val (integer, decimal) = MoneyUtils.transToShowMoney(res)
        setCalculatorState(CalculatorState(
            number1 = integer,
            number1HasDecimal = decimal.isNotEmpty(),
            number1Decimal = decimal
        ))
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (calcState.number2.isNotEmpty() || calcState.number2HasDecimal) {
            calculate()
        }
        if (calcState.number1.isNotEmpty() || calcState.number1HasDecimal) {
            setCalculatorState(calcState.copy(
                operation = operation
            ))
        }
    }

    private fun enterNumber(digit: Int) {
        if (calcState.operation == null) {
            if (calcState.number1HasDecimal) {
                if (calcState.number1Decimal.length < MAX_DECIMAL_LENGTH) {
                    setCalculatorState(calcState.copy(
                        number1Decimal = calcState.number1Decimal + digit
                    ))
                }
            } else {
                if (calcState.number1.isEmpty() && digit == 0) {
                    return
                }
                if (calcState.number1.length < MAX_NUMBER_LENGTH) {
                    setCalculatorState(calcState.copy(
                        number1 = calcState.number1 + digit
                    ))
                }
            }
        } else {
            if (calcState.number2HasDecimal) {
                if (calcState.number2Decimal.length < MAX_DECIMAL_LENGTH) {
                    setCalculatorState(calcState.copy(
                        number2Decimal = calcState.number2Decimal + digit
                    ))
                }
            } else {
                if (calcState.number2.isEmpty() && digit == 0) {
                    return
                }
                if (calcState.number2.length < MAX_NUMBER_LENGTH) {
                    setCalculatorState(calcState.copy(
                        number2 = calcState.number2 + digit
                    ))
                }
            }
        }
    }

    private fun delete() {
        val state = when {
            calcState.number2Decimal.isNotEmpty() -> calcState.copy(
                number2Decimal = calcState.number2Decimal.dropLast(1)
            )
            calcState.number2HasDecimal -> calcState.copy(
                number2HasDecimal = false
            )
            calcState.number2.isNotEmpty() -> calcState.copy(
                number2 = calcState.number2.dropLast(1)
            )
            calcState.operation != null -> calcState.copy(
                operation = null
            )
            calcState.number1Decimal.isNotEmpty() -> calcState.copy(
                number1Decimal = calcState.number1Decimal.dropLast(1)
            )
            calcState.number1HasDecimal -> calcState.copy(
                number1HasDecimal = false
            )
            calcState.number1.isNotEmpty() -> calcState.copy(
                number1 = calcState.number1.dropLast(1)
            )
            else -> null
        }
        state?.apply { setCalculatorState(this) }
    }
}