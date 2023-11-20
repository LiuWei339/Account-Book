package com.wl.accountbook.ui.record

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.common.BottomDialog
import com.wl.accountbook.ui.common.DayPicker
import com.wl.accountbook.ui.common.NavigationBarFiller
import com.wl.accountbook.ui.common.StatusBarFiller
import com.wl.accountbook.ui.record.calculator.CalculatorState
import com.wl.accountbook.ui.record.components.RecordInput
import com.wl.accountbook.ui.record.components.RecordTopBar
import com.wl.accountbook.ui.record.components.RecordTypeSelector
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.LightGrayBg
import com.wl.common.util.LogUtil
import com.wl.common.util.toLocalDate
import com.wl.common.util.toLocalDayString
import com.wl.common.util.toTimeMillis
import java.util.Date

@SuppressLint("UnrememberedMutableState")
@Composable
fun RecordEditScreen(
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
                    pluralStringResource(id = R.plurals.expense, count = 1),
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
                    showTime = state.date.toLocalDayString(),
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
            BottomDialog {
                DayPicker(
                    curDate = state.date.toLocalDate(),
                    onClose = { onAction(RecordAction.CloseDatePicker) },
                    onConfirm = { localDate ->
                        LogUtil.d("AddRecordScreen", "select date: $localDate")
                        onAction(RecordAction.SelectDate(localDate.toTimeMillis()))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun AddRecordScreenPreview() {
    AccountBookTheme(dynamicColor = false) {
        RecordEditScreen(
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
        RecordEditScreen(
            state = RecordState(
                note = "note",
                date = Date(),
                typeIndexId = 0
            ),
            calcState = CalculatorState(),
            onAction = {}
        )
    }
}