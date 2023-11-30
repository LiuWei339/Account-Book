package com.wl.accountbook.ui.record.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.components.Emoji
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.domain.model.MoneyRecordType

@Composable
fun RecordTypeSelector(
    modifier: Modifier = Modifier,
    recordTypes: List<MoneyRecordType>,
    selectedIndex: Int,
    onClickType: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        columns = GridCells.Adaptive(minSize = Dimens.MoneyTypeIconSize * 1.8f),
        contentPadding = PaddingValues(
            vertical = Dimens.PaddingSmall,
            horizontal = Dimens.PaddingXXXLarge
        ),
        verticalArrangement = Arrangement.spacedBy(Dimens.PaddingSmall)
    ) {
        items(
            count = recordTypes.size,
            key = { index -> recordTypes[index].id }
        ) {
            SingletonRecordType(
                type = recordTypes[it],
                selected = it == selectedIndex
            ) {
                onClickType(it)
            }
        }
    }
}

@Composable
private fun SingletonRecordType(
    type: MoneyRecordType,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Emoji(
            emoji = type.emoji,
            size = Dimens.MoneyTypeIconSize
        )

        Text(
            text = type.name,
            style = MaterialTheme.typography.labelSmall.run {
                if (selected) {
                    this.copy(color = MaterialTheme.colorScheme.primary)
                } else {
                    this
                }
            }
        )
    }
}

@Preview
@Composable
fun RecordTypeSelectorPreview() {
    AccountBookTheme(dynamicColor = false) {
        val recordTypes = remember(key1 = true) {
            val moneyRecord = MoneyRecordType(
                1,
                "Food",
                "🎮",
                true
            )
            val list = ArrayList<MoneyRecordType>()
            repeat(9) {
                list.add(moneyRecord.copy(id = it))
            }
            list
        }

        RecordTypeSelector(
            recordTypes = recordTypes,
            selectedIndex = 0,
            onClickType = {}
        )
    }
}

@Preview
@Composable
fun SingletonRecordTypePreview() {
    AccountBookTheme(dynamicColor = false) {
        SingletonRecordType(
            MoneyRecordType(
                1,
                "Food",
                "🎮",
                true
            ),
            selected = true,
            onClick = {}
        )
    }
}