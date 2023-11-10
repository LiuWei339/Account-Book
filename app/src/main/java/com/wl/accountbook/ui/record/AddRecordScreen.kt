package com.wl.accountbook.ui.record

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.common.NavigationBarFiller
import com.wl.accountbook.ui.common.StatusBarFiller
import com.wl.accountbook.ui.record.calculator.CalculatorState
import com.wl.accountbook.ui.record.components.RecordInput
import com.wl.accountbook.ui.record.components.RecordTopBar
import com.wl.accountbook.ui.record.components.RecordTypeSelector
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.LightGrayBg

@Composable
fun AddRecordScreen(
    modifier: Modifier = Modifier,
    state: RecordState,
    calcState: CalculatorState,
    onAction: (RecordAction) -> Unit
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
                    onAction(RecordAction.Back)
                }
            )

            RecordTypeSelector(
                modifier = Modifier.fillMaxWidth().weight(1f),
                recordTypes = state.recordTypes,
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
                    calcState = calcState,
                    onCalcAction = {
                        onAction(it)
                    },
                    onClickDate = {
                        onAction(RecordAction.ClickDate)
                    },
                    onChangeNote = {
                        onAction(RecordAction.ChangeNote(it))
                    }
                )
            }

            NavigationBarFiller(color = LightGrayBg)
        }
    }
}

@Preview
@Composable
fun AddRecordScreenPreview() {
    AccountBookTheme(dynamicColor = false) {
        AddRecordScreen(
            state = RecordState(
                number = "0",
                note = "note",
                timeStamp = System.currentTimeMillis(),
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
                number = "0",
                note = "note",
                timeStamp = System.currentTimeMillis(),
                showTime = "2023/10/30",
                typeIndexId = 0
            ),
            calcState = CalculatorState(),
            onAction = {}
        )
    }
}