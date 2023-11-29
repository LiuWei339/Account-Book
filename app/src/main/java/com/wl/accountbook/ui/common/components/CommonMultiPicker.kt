package com.wl.accountbook.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.theme.Gray
import kotlinx.coroutines.delay

@Composable
fun CommonMultiPicker(
    modifier: Modifier = Modifier,
    title: String = "",
    pickerList: List<List<String>> = emptyList(),
    initSelected: List<Int> = emptyList(),
    onClose: () -> Unit,
    onConfirm: (List<Int>) -> Unit
) {

    var selected by remember {
        mutableStateOf(initSelected)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.DialogTitleHeight)
                    .padding(horizontal = Dimens.PaddingLarge),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.cancel).uppercase(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Gray),
                    modifier = Modifier.clickable { onClose() })

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(text = stringResource(id = R.string.confirm).uppercase(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Gray),
                    modifier = Modifier
                        .clickable {
                            onConfirm(selected.toList())
                            onClose()
                        })
            }

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                pickerList.forEachIndexed { index, list ->
                    ScrollablePicker(
                        items = list,
                        initialIndex = initSelected[index],
                        onChange = {
                            selected = selected.toMutableList().apply {
                                set(index, it)
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun ScrollablePicker(
    items: List<String>,
    initialIndex: Int = 0,
    pickerItemHeight: Dp = CommonPickerConstants.DEFAULT_PICKER_ITEM_HEIGHT,
    showItemsCount: Int = 3,
    modifier: Modifier = Modifier,
    onChange: (Int) -> Unit
) {
    val indexOffset = remember(showItemsCount) {
        showItemsCount / 2
    }
    val showList = remember(items, indexOffset) {
        val list = ArrayList<String>()
        repeat(indexOffset) { list.add("") }
        list.addAll(items)
        repeat(indexOffset) { list.add("") }
        list
    }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val selectedIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val scrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    var offsetChangeNotifier by remember { mutableStateOf(false) }

    LaunchedEffect(scrollOffset) {
        delay(100)
        offsetChangeNotifier = !offsetChangeNotifier
    }

    LaunchedEffect(key1 = offsetChangeNotifier) {
        listState.animateScrollToItem(selectedIndex)
        onChange(selectedIndex)
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .height(pickerItemHeight * showItemsCount)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(showList) { index, value ->
            PickerItem(
                itemText = value,
                isSelected = (selectedIndex + indexOffset) == index,
                modifier = Modifier
                    .height(pickerItemHeight)
                    .widthIn(min = CommonPickerConstants.DEFAULT_PICKER_ITEM_WIDTH)
            )
        }
    }
}

@Composable
fun PickerItem(
    itemText: String, isSelected: Boolean, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Text(
            text = itemText, style = MaterialTheme.typography.bodySmall.copy(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Gray,
                fontSize = CommonPickerConstants.PICKER_FONT_SIZE * if (isSelected) 1.3 else 1.0,
            ), textAlign = TextAlign.Center
        )
    }
}

private object CommonPickerConstants {
    val DEFAULT_PICKER_ITEM_HEIGHT = 56.dp
    val DEFAULT_PICKER_ITEM_WIDTH = 56.dp
    val PICKER_FONT_SIZE = 14.sp
}
