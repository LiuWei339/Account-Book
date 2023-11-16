package com.wl.accountbook.ui.record

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.NavigationBarFiller
import com.wl.accountbook.ui.common.StatusBarFiller
import com.wl.accountbook.ui.record.calculator.CalculatorState
import com.wl.accountbook.ui.record.components.RecordInput
import com.wl.accountbook.ui.record.components.RecordTopBar
import com.wl.accountbook.ui.record.components.RecordTypeSelector
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.LightGrayBg
import com.wl.common.util.LogUtil
import java.time.LocalDate

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    modifier: Modifier = Modifier,
    state: RecordState,
    calcState: CalculatorState,
    onAction: (RecordAction) -> Unit,
    navigateUp: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            StatusBarFiller()

            RecordTopBar(
                tabNames = listOf(
                    stringResource(id = R.string.expenses),
                    stringResource(id = R.string.income)
                ),
                selected = state.tabIndex,
                onChangeTab = {
                    onAction(
                        if (it == 0) RecordAction.PressExpenseTab else RecordAction.PressIncomeTab
                    )
                },
                onClickBack = {
                    navigateUp()
                }
            )

            RecordTypeSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                recordTypes = state.recordTypes,
                selectedIndex = state.typeIndexId,
                onClickType = {
                    onAction(RecordAction.SelectType(it))
                }
            )

            if (state.typeIndexId != -1) {
                RecordInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    showTime = state.showTime,
                    note = state.note,
                    isValidRecord = state.isValidRecord,
                    calcState = calcState,
                    onCalcAction = {
                        onAction(it)
                    },
                    onClickDate = {
                        onAction(RecordAction.ClickDate)
                    },
                    onChangeNote = {
                        onAction(RecordAction.ChangeNote(it))
                    },
                    navigateUp = navigateUp
                )
            }

            NavigationBarFiller(color = LightGrayBg)
        }

        if (state.showDateSelector) {
            // TODO
//            val datePickerState = rememberDatePickerState()
//            val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
//            DatePickerDialog(
//                onDismissRequest = { onAction(RecordAction.CloseDatePicker) },
//                confirmButton = {
//                    TextButton(
//                        onClick = {
//                            onAction(RecordAction.SelectDate(datePickerState.selectedDateMillis ?: 0L))
//                        },
//                        enabled = confirmEnabled.value
//                    ) {
//                        Text(stringResource(id = R.string.confirm))
//                    }
//                },
//                dismissButton = {
//                    TextButton(
//                        onClick = {
//                            onAction(RecordAction.CloseDatePicker)
//                        }
//                    ) {
//                        Text(stringResource(id = R.string.cancel))
//                    }
//                }
//            ) {
//                DatePicker(
//                    state = datePickerState,
//                    showModeToggle = false,
//                    title = null,
//                    headline = null
//                )
//            }
        }
    }
}

@Preview
@Composable
fun AddRecordScreenPreview() {
    AccountBookTheme(dynamicColor = false) {
        AddRecordScreen(
            state = RecordState(
                note = "note",
                typeIndexId = -1
            ),
            calcState = CalculatorState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
fun AddRecordScreenWithKeyboardPreview() {
    AccountBookTheme(dynamicColor = false) {
        AddRecordScreen(
            state = RecordState(
                note = "note",
                showTime = "2023/10/30",
                typeIndexId = 0
            ),
            calcState = CalculatorState(),
            onAction = {}
        )
    }
}