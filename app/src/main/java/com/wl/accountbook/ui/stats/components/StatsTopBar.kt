package com.wl.accountbook.ui.stats.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.components.BottomDialog
import com.wl.accountbook.ui.common.components.CommonTab
import com.wl.accountbook.ui.stats.StatsAction
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.common.util.toTimeMillis
import java.time.LocalDate

@Composable
fun StatsTopBar(
    modifier: Modifier = Modifier,
    timeText: String,
    selected: Int,
    onAction: (StatsAction) -> Unit
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StatsDate(
            timeText = timeText,
            onAction = onAction
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CommonTab(
                label = pluralStringResource(id = R.plurals.expense, count = 1),
                isSelected = 0 == selected,
                onClick = {
                    onAction(StatsAction.PressExpenseTab)
                }
            )
            CommonTab(
                label = stringResource(id = R.string.income),
                isSelected = 1 == selected,
                onClick = {
                    onAction(StatsAction.PressIncomeTab)
                }
            )
        }
    }
}

@Composable
fun StatsDate(
    modifier: Modifier = Modifier,
    timeText: String,
    onAction: (StatsAction) -> Unit
) {
    var showMonthSelector by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(vertical = Dimens.PaddingXSmall)
            .clickable { showMonthSelector = true }
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = timeText,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(Dimens.PaddingXSmall),
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(id = R.drawable.ic_down_arrow),
            contentDescription = null,
            modifier = Modifier.size(Dimens.HeadIconSize)
        )
    }

    if (showMonthSelector) {
        BottomDialog(onDismiss = {showMonthSelector = false}) {
            StatsDatePicker(
                curDate = LocalDate.now(),
                onClose = { showMonthSelector = false },
                onConfirm = { localDate, isSelectYear ->
                    if (isSelectYear) {
                        onAction(StatsAction.SelectYear(localDate.toTimeMillis()))
                    } else {
                        onAction(StatsAction.SelectMonth(localDate.toTimeMillis()))
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun StatsTopBarPreview() {
    AccountBookTheme(dynamicColor = false) {
        StatsTopBar(
            timeText = "Nov 11",
            selected = 0,
            onAction = {}
        )
    }
}