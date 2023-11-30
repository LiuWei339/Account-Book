package com.wl.accountbook.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.components.AutoSizeText
import com.wl.data.util.MoneyUtils
import kotlin.math.abs

@Composable
fun SingleStateOfTheMonth(
    label: String,
    amount: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
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
                MoneyUtils.transToActualMoney(income - expenses),
                modifier
            )
            Spacer(modifier = Modifier.width(Dimens.PaddingExLarge))
        }
    }
}