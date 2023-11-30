package com.wl.accountbook.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.ui.common.components.BottomDialog
import com.wl.accountbook.ui.common.components.MonthPicker
import com.wl.accountbook.ui.common.components.StatusBarFiller
import com.wl.accountbook.ui.home.components.DaysRecords
import com.wl.accountbook.ui.home.components.HomeTopBar
import com.wl.accountbook.ui.home.components.StatesOfTheMonth
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.common.util.LogUtil
import com.wl.common.util.toLocalDate
import com.wl.common.util.toTimeMillis
import com.wl.common.util.tomorrow
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import java.util.Date

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    navigateToDetail: (createTime: Long) -> Unit,
    navigateToSearch: () -> Unit
) {
    var showMonthSelector by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        StatusBarFiller()
        HomeTopBar(
            date = state.date,
            onDateClick = { showMonthSelector = true },
            onSearchClick = navigateToSearch
        )

        StatesOfTheMonth(state.totalIncome, state.totalExpenses)

        DaysRecords(recordsByDay = state.recordsByDay, navigateToDetail = navigateToDetail)
    }

    if (showMonthSelector) {
        BottomDialog(onDismiss = {showMonthSelector = false}) {
            MonthPicker(
                curDate = state.date.toLocalDate(),
                onClose = { showMonthSelector = false },
                onConfirm = { localDate ->
                    LogUtil.d("HomeScreen", "select date: $localDate")
                    onAction(HomeAction.SelectDate(localDate.toTimeMillis()))
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AccountBookTheme(dynamicColor = false) {
        HomeScreen(
            state = HomeState(
                recordsByDay = listOf(
                    Date() to listOf(
                        MoneyRecordAndType(
                            20,
                            MoneyRecordType(
                                1,
                                "Food",
                                "🎮",
                                true
                            ),
                            "",
                            Date().time,
                            Date().time,
                        ),
                        MoneyRecordAndType(
                            10,
                            MoneyRecordType(
                                2,
                                "Food1",
                                "🏓",
                                false
                            ),
                            "",
                            Date().time + 1,
                            Date().time + 1,
                        )
                    ),
                    Date().tomorrow() to listOf(
                        MoneyRecordAndType(
                            20,
                            MoneyRecordType(
                                1,
                                "Food",
                                "🎮",
                                true
                            ),
                            "",
                            Date().time + 2,
                            Date().time + 2,
                        ),
                        MoneyRecordAndType(
                            10,
                            MoneyRecordType(
                                2,
                                "Food1",
                                "🏓",
                                false
                            ),
                            "",
                            Date().time + 3,
                            Date().time + 3,
                        )
                    ),
                )
            ),
            onAction = { },
            navigateToDetail = {},
            navigateToSearch = {}
        )
    }
}
