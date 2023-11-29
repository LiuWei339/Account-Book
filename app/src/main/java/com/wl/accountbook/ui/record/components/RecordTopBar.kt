package com.wl.accountbook.ui.record.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.components.CommonTab
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabNames.forEachIndexed { index, name ->
                CommonTab(
                    modifier = Modifier.padding(vertical = Dimens.PaddingSmall),
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