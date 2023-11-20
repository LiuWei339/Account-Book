package com.wl.accountbook.ui.record

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wl.accountbook.ui.record.calculator.CalculatorAction
import com.wl.accountbook.ui.record.calculator.CalculatorOperation
import com.wl.accountbook.ui.record.calculator.CalculatorState
import com.wl.data.util.Constants.MAX_DECIMAL_LENGTH
import com.wl.data.util.Constants.MAX_NUMBER_LENGTH
import com.wl.data.util.MoneyUtils
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import com.wl.domain.repository.RecordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recordRepo: RecordRepo
) : ViewModel() {

    private val recordArgs = RecordArgs(savedStateHandle)
    private var allRecordTypes: List<MoneyRecordType> = emptyList()

    var calcState by mutableStateOf(CalculatorState())
        private set

    var recordState by mutableStateOf(
        RecordState(
            date = Date(),
            tabIndex = 0
        )
    )
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allRecordTypes = recordRepo.getRecordTypes().first()

            if (isAddRecord()) { // Add record
                withContext(Dispatchers.Main) {
                    recordState = recordState.copy(
                        recordTypes = allRecordTypes.filter { it.isExpenses }
                    )
                }
            } else { // Edit record
                val recordAndType = recordRepo.getRecordAndType(recordArgs.recordCreateTime)
                val tabRecordTypes =
                    allRecordTypes.filter { it.isExpenses == recordAndType.type.isExpenses }
                val typeIndex = tabRecordTypes.indexOfFirst { it.id == recordAndType.type.id }

                withContext(Dispatchers.Main) {
                    MoneyUtils.transToShowMoney(recordAndType.amount).apply {
                        calcState = calcState.copy(
                            number1 = first,
                            number1HasDecimal = second.isNotEmpty(),
                            number1Decimal = second
                        )
                    }

                    recordState = recordState.copy(
                        note = recordAndType.note,
                        date = Date(recordAndType.recordTime),
                        typeIndexId = typeIndex,
                        tabIndex = if (recordAndType.type.isExpenses)
                            RecordAction.PressExpenseTab.tabIndex
                        else RecordAction.PressIncomeTab.tabIndex,
                        recordTypes = tabRecordTypes,
                        isValidRecord = true
                    )
                }
            }
        }
    }

    fun isAddRecord(): Boolean {
        return recordArgs.recordCreateTime < 0
    }

    private fun setCalculatorState(state: CalculatorState) {
        recordState = recordState.copy(
            isValidRecord = isValidRecord(state)
        )
        calcState = state
    }

    private fun isValidRecord(state: CalculatorState): Boolean {
        return state.operation == null
                && MoneyUtils.transToCalcMoney(state.number1, state.number1Decimal) > 0
    }

    fun onRecordAction(action: RecordAction) {
        when (action) {
            is RecordAction.PressTab -> {
                if (recordState.tabIndex == action.tabIndex) return
                if (action is RecordAction.PressExpenseTab) {
                    allRecordTypes.filter { it.isExpenses }
                } else {
                    allRecordTypes.filter { !it.isExpenses }
                }.apply {
                    recordState = recordState.copy(
                        tabIndex = action.tabIndex,
                        recordTypes = this,
                        typeIndexId = -1
                    )
                }
            }

            is RecordAction.SelectType -> {
                recordState = recordState.copy(
                    typeIndexId = action.index
                )
            }

            is RecordAction.ClickDate -> {
                recordState = recordState.copy(
                    showDateSelector = true
                )
            }

            is RecordAction.CloseDatePicker -> {
                recordState = recordState.copy(
                    showDateSelector = false
                )
            }

            is RecordAction.SelectDate -> {
                recordState = recordState.copy(
                    date = Date(action.timeStamp),
                )
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

    private fun addOrUpdateRecord() {
        if (!isValidRecord(calcState)) return
        viewModelScope.launch(Dispatchers.IO) {
            val record = MoneyRecordAndType(
                amount = MoneyUtils.transToCalcMoney(calcState.number1, calcState.number1Decimal),
                type = recordState.recordTypes[recordState.typeIndexId],
                note = recordState.note,
                recordTime = recordState.date.time,
                createTime = if (isAddRecord()) System.currentTimeMillis() else recordArgs.recordCreateTime
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
                setCalculatorState(
                    calcState.copy(
                        number1HasDecimal = true
                    )
                )
            }
        } else {
            if (!calcState.number2HasDecimal) {
                setCalculatorState(
                    calcState.copy(
                        number2HasDecimal = true
                    )
                )
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
            addOrUpdateRecord()
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
        setCalculatorState(
            CalculatorState(
                number1 = integer,
                number1HasDecimal = decimal.isNotEmpty(),
                number1Decimal = decimal
            )
        )
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (calcState.number2.isNotEmpty() || calcState.number2HasDecimal) {
            calculate()
        }
        if (calcState.number1.isNotEmpty() || calcState.number1HasDecimal) {
            setCalculatorState(
                calcState.copy(
                    operation = operation
                )
            )
        }
    }

    private fun enterNumber(digit: Int) {
        if (calcState.operation == null) {
            if (calcState.number1HasDecimal) {
                if (calcState.number1Decimal.length < MAX_DECIMAL_LENGTH) {
                    setCalculatorState(
                        calcState.copy(
                            number1Decimal = calcState.number1Decimal + digit
                        )
                    )
                }
            } else {
                if (calcState.number1.isEmpty() && digit == 0) {
                    return
                }
                if (calcState.number1.length < MAX_NUMBER_LENGTH) {
                    setCalculatorState(
                        calcState.copy(
                            number1 = calcState.number1 + digit
                        )
                    )
                }
            }
        } else {
            if (calcState.number2HasDecimal) {
                if (calcState.number2Decimal.length < MAX_DECIMAL_LENGTH) {
                    setCalculatorState(
                        calcState.copy(
                            number2Decimal = calcState.number2Decimal + digit
                        )
                    )
                }
            } else {
                if (calcState.number2.isEmpty() && digit == 0) {
                    return
                }
                if (calcState.number2.length < MAX_NUMBER_LENGTH) {
                    setCalculatorState(
                        calcState.copy(
                            number2 = calcState.number2 + digit
                        )
                    )
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