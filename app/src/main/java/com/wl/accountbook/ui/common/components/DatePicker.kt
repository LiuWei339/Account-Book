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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.Gray
import com.wl.common.util.LogUtil
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.temporal.ChronoField

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    curDate: LocalDate = LocalDate.now(),
    title: String = "",
    showDay: Boolean = true,
    showMonth: Boolean = true,
    onClose: () -> Unit,
    onConfirm: (LocalDate) -> Unit
) {

    val yearList = remember(curDate.year) { (2020..LocalDate.now().year).toList() }
    var selectDate by remember { mutableStateOf(curDate) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.DialogTitleHeight)
                    .padding(horizontal = Dimens.PaddingLarge)
            ) {
                Text(text = stringResource(id = R.string.cancel).uppercase(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Gray),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { onClose() })

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                Text(text = stringResource(id = R.string.confirm).uppercase(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Gray),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onConfirm(selectDate)
                            onClose()
                        })
            }

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScrollablePicker(items = yearList.map { it.toString() },
                    initialIndex = yearList.indexOf(curDate.year),
                    onChange = {
                        selectDate = selectDate.withYear(yearList[it])
                    })

                if (showMonth) {
                    val monthList = remember { (1..12).toList() }
                    ScrollablePicker(items = monthList.map { it.toString() },
                        initialIndex = monthList.indexOf(curDate.monthValue),
                        onChange = {
                            selectDate = selectDate.withMonth(monthList[it])
                        })
                }

                if (showDay) {
                    val maxDayInTheMonth by remember { derivedStateOf { curDate.range(ChronoField.DAY_OF_MONTH).maximum.toInt() } }
                    val dayList = remember(maxDayInTheMonth) { (1..maxDayInTheMonth).toList() }
                    ScrollablePicker(items = dayList.map { it.toString() },
                        initialIndex = dayList.indexOf(curDate.dayOfMonth),
                        onChange = {
                            selectDate = selectDate.withDayOfMonth(dayList[it])
                        })
                }
            }
        }
    }
}

@Composable
fun DayPicker(
    modifier: Modifier = Modifier,
    curDate: LocalDate = LocalDate.now(),
    onClose: () -> Unit,
    onConfirm: (LocalDate) -> Unit
) {
    DatePicker(
        modifier = modifier,
        curDate = curDate,
        title = stringResource(id = R.string.select_date).uppercase(),
        showDay = true,
        showMonth = true,
        onClose = onClose,
        onConfirm = onConfirm
    )
}

@Composable
fun MonthPicker(
    modifier: Modifier = Modifier,
    curDate: LocalDate = LocalDate.now(),
    onClose: () -> Unit,
    onConfirm: (LocalDate) -> Unit
) {
    DatePicker(
        modifier = modifier,
        curDate = curDate,
        title = stringResource(id = R.string.select_month).uppercase(),
        showDay = false,
        showMonth = true,
        onClose = onClose,
        onConfirm = onConfirm
    )
}

@Composable
fun YearPicker(
    modifier: Modifier = Modifier,
    curDate: LocalDate = LocalDate.now(),
    onClose: () -> Unit,
    onConfirm: (LocalDate) -> Unit
) {
    DatePicker(
        modifier = modifier,
        curDate = curDate,
        title = stringResource(id = R.string.select_year).uppercase(),
        showDay = false,
        showMonth = false,
        onClose = onClose,
        onConfirm = onConfirm
    )
}

@Preview
@Composable
fun ScrollablePickerDatePreview() {
    AccountBookTheme(dynamicColor = false) {
        DatePicker(onClose = {}, onConfirm = {
            LogUtil.d("test1", it.toString())
        }, modifier = Modifier.statusBarsPadding(), title = "Select date"
        )
    }
}

@Preview
@Composable
fun ScrollablePickerMonthPreview() {
    AccountBookTheme(dynamicColor = false) {
        DatePicker(onClose = {}, onConfirm = {
            LogUtil.d("test1", it.toString())
        }, modifier = Modifier.statusBarsPadding(), title = "Select month", showDay = false
        )
    }
}

@Composable
fun ScrollablePicker(
    items: List<String>,
    initialIndex: Int = 0,
    pickerItemHeight: Dp = DatePickerConstants.DEFAULT_PICKER_ITEM_HEIGHT,
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
                    .widthIn(min = DatePickerConstants.DEFAULT_PICKER_ITEM_WIDTH)
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
                fontSize = DatePickerConstants.PICKER_FONT_SIZE * if (isSelected) 1.3 else 1.0,
            ), textAlign = TextAlign.Center
        )
    }
}

private object DatePickerConstants {
    val DEFAULT_PICKER_ITEM_HEIGHT = 56.dp
    val DEFAULT_PICKER_ITEM_WIDTH = 56.dp
    val PICKER_FONT_SIZE = 14.sp
}