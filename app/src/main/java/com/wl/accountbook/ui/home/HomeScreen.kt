package com.wl.accountbook.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.OnLifecycleEvent
import com.wl.accountbook.ui.common.components.AutoSizeText
import com.wl.accountbook.ui.common.components.BottomDialog
import com.wl.accountbook.ui.common.components.MonthPicker
import com.wl.accountbook.ui.common.components.StatusBarFiller
import com.wl.accountbook.ui.home.components.DaysRecords
import com.wl.accountbook.ui.home.components.HomeTopBar
import com.wl.accountbook.ui.search.SearchAction
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.common.util.LogUtil
import com.wl.common.util.toLocalDate
import com.wl.common.util.toTimeMillis
import com.wl.common.util.tomorrow
import com.wl.data.util.MoneyUtils
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import java.util.Date
import kotlin.math.abs

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

@Composable
fun SingleStateOfTheMonth(
    label: String,
    amount: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color = style.color
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            AutoSizeText(
                text = amount,
                style = style
            )
        }
    }
}

@Composable
fun StatesOfTheMonth(
    income: Long, expenses: Long
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondary),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.PaddingLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(Dimens.PaddingExLarge))
            val modifier = Modifier
                .height(Dimens.MonthSingleStateHeight)
                .weight(1f)
                .fillMaxWidth()
            SingleStateOfTheMonth(
                pluralStringResource(id = R.plurals.expense, count = 2),
                MoneyUtils.transToActualMoney(expenses),
                modifier
            )
            Spacer(modifier = Modifier.width(Dimens.PaddingExLarge))
            SingleStateOfTheMonth(
                stringResource(id = R.string.income),
                MoneyUtils.transToActualMoney(income),
                modifier
            )
            Spacer(modifier = Modifier.width(Dimens.PaddingExLarge))
            SingleStateOfTheMonth(
                stringResource(id = R.string.total),
                "${if (expenses > income) "-" else ""}" +
                        "${MoneyUtils.transToActualMoney(abs(income - expenses))}",
                modifier
            )
            Spacer(modifier = Modifier.width(Dimens.PaddingExLarge))
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
