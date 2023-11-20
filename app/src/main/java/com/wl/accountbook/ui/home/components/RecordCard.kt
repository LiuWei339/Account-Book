package com.wl.accountbook.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.HorizontalDivider
import com.wl.accountbook.ui.home.HomeAction
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.TextGray
import com.wl.common.util.dayWithWeekFormat
import com.wl.data.util.toActualMoney
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import java.util.Date

@Composable
fun SingleRecordCard(
    record: MoneyRecordAndType,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.SingleRecordHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // icon TODO
                Text(text = record.type.emoji)

                Spacer(modifier = Modifier.width(Dimens.PaddingLarge))

                // type or note
                Text(
                    text = record.note.ifEmpty { record.type.name },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Text(
            text = "${if (record.type.isExpenses) "-" else ""}${record.amount.toActualMoney()}",
            style = MaterialTheme.typography.bodyMedium.copy(color = TextGray)
        )
    }
}

@Composable
fun DayRecordsTitle(
    date: Date,
    income: String,
    expense: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(Dimens.DayRecordHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = date.dayWithWeekFormat(),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.weight(1f))

        Box {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.income_amount, income),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(Dimens.PaddingXLarge))
                Text(
                    text = pluralStringResource(id = R.plurals.expense, count = 2),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun DayRecords(
    date: Date,
    records: List<MoneyRecordAndType>,
    modifier: Modifier = Modifier,
    onAction: (HomeAction) -> Unit,
    navigateToDetail: (createTime: Long) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val income = remember(records) {
            records
                .filter { !it.type.isExpenses }
                .sumOf { it.amount }
                .toActualMoney()
        }
        val expense = remember(records) {
            records
                .filter { it.type.isExpenses }
                .sumOf { it.amount }
                .toActualMoney()
        }

        DayRecordsTitle(
            date = date, income = income, expense = expense,
            modifier = Modifier.padding(horizontal = Dimens.PaddingXLarge)
        )

        HorizontalDivider(1f)

        Column {
            records.forEach { record ->
                SingleRecordCard(
                    record,
                    modifier = Modifier.padding(horizontal = Dimens.PaddingLarge).clickable {
                        navigateToDetail(record.createTime)
                    }
                )
            }
        }
    }
}

@Composable
fun DaysRecords(
    recordsByDay: List<Pair<Date, List<MoneyRecordAndType>>>,
    modifier: Modifier = Modifier,
    onAction: (HomeAction) -> Unit,
    navigateToDetail: (createTime: Long) -> Unit
) {
    LazyColumn {
        items(
            items = recordsByDay,
            key = { it.first }
        ) { (date, records) ->
            DayRecords(
                date = date,
                records = records,
                onAction = onAction,
                navigateToDetail = navigateToDetail
            )
        }
    }
}

@Preview
@Composable
fun SingleRecordCardPreview() {
    AccountBookTheme() {
        SingleRecordCard(
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
            )
        )
    }
}

@Preview
@Composable
fun DayRecordsPreview() {
    AccountBookTheme() {
        DayRecords(
            date = Date(),
            records = listOf(
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
            onAction = {},
            navigateToDetail = {}
        )
    }
}