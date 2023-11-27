package com.wl.accountbook.ui.record.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.components.bottomBorder
import com.wl.accountbook.ui.theme.AccountBookTheme

@Composable
fun RecordTopBar(
    modifier: Modifier = Modifier,
    tabNames: List<String>,
    selected: Int = 0,
    onChangeTab: (Int) -> Unit,
    onClickBack: () -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .height(Dimens.HeadSize)
            .padding(horizontal = Dimens.PaddingXLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_left_arrow),
            contentDescription = null,
            modifier = Modifier
                .size(Dimens.HeadIconSize)
                .clickable {
                    onClickBack()
                }
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabNames.forEachIndexed { index, name ->
                RecordTopBarTab(
                    label = name,
                    isSelected = index == selected,
                    onClick = {
                        onChangeTab(index)
                    }
                )
            }
        }
    }
}

@Composable
private fun RecordTopBarTab(
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {

    Box(modifier = Modifier
        .bottomBorder(
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
            strokeWidth = 2.dp
        )
        .fillMaxHeight()
        .clickable {
            onClick()
        },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun RecordTopBarPreview() {
    AccountBookTheme(dynamicColor = false) {
        RecordTopBar(
            tabNames = listOf(
                pluralStringResource(id = R.plurals.expense, count = 1),
                stringResource(id = R.string.income)
            ),
            selected = 0,
            onChangeTab = {},
            onClickBack = {}
        )
    }
}