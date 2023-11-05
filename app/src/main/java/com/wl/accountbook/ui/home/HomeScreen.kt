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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.AutoSizeText
import com.wl.accountbook.ui.common.StatusBarFiller
import com.wl.accountbook.ui.home.components.DayRecords
import com.wl.accountbook.ui.home.components.HomeTopBar
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.common.util.tomorrow
import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordType
import java.util.Date
import kotlin.math.abs

@Composable
fun HomeScreen(
    recordsByDay: List<Pair<Date, List<MoneyRecord>>>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StatusBarFiller()
        HomeTopBar("Oct 2023", {}, {})
        StatesOfTheMonth(10000000L, 20L)

        val sortedRecordsByDay = remember(recordsByDay) {
            recordsByDay.sortedByDescending { it.first.time }
        }

        LazyColumn {
            items(
                items = recordsByDay,
                key = { it.first }
            ) { (date, records) ->
                DayRecords(
                    date = date,
                    records = records
                )
            }
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
                stringResource(id = R.string.expenses),
                "$$expenses",
                modifier
            )
            Spacer(modifier = Modifier.width(Dimens.PaddingExLarge))
            SingleStateOfTheMonth(
                stringResource(id = R.string.income),
                "$$income",
                modifier
            )
            Spacer(modifier = Modifier.width(Dimens.PaddingExLarge))
            SingleStateOfTheMonth(
                stringResource(id = R.string.total),
                "${if (expenses > income) "-" else ""}$${abs(income - expenses)}",
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
            listOf(
                Date() to listOf(
                    MoneyRecord(
                        20,
                        MoneyRecordType(
                            1,
                            "Food",
                            "🎮",
                            true
                        ),
                        Date().time,
                        ""
                    ),
                    MoneyRecord(
                        10,
                        MoneyRecordType(
                            2,
                            "Food1",
                            "🏓",
                            false
                        ),
                        Date().time + 1,
                        ""
                    )
                ),
                Date().tomorrow() to listOf(
                    MoneyRecord(
                        20,
                        MoneyRecordType(
                            1,
                            "Food",
                            "🎮",
                            true
                        ),
                        Date().time + 2,
                        ""
                    ),
                    MoneyRecord(
                        10,
                        MoneyRecordType(
                            2,
                            "Food1",
                            "🏓",
                            false
                        ),
                        Date().time + 3,
                        ""
                    )
                ),
            )
        )
    }
}
