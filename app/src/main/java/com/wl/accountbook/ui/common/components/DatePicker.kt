package com.wl.accountbook.ui.common.components

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.common.util.LogUtil
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
    val monthList = remember { (1..12).toList() }

    var pickerList by remember {
        mutableStateOf(
            ArrayList<List<String>>().apply {
                add(yearList.map { it.toString() })
                if (showMonth) {
                    add(monthList.map { it.toString() })
                }
                if (showDay) {
                    add(emptyList())
                }
            }.toList()
        )
    }

    var dayIndex = 0
    var dayList = emptyList<Int>()
    if (showDay) {
        val maxDayInTheMonth by remember { derivedStateOf { curDate.range(ChronoField.DAY_OF_MONTH).maximum.toInt() } }
        dayList = remember(maxDayInTheMonth) { (1..maxDayInTheMonth).toList() }
        pickerList = remember(dayList) {
            dayIndex = dayList.indexOf(curDate.dayOfMonth)
            pickerList.toMutableList().apply {
                set(2, dayList.map { it.toString() })
            }
        }
    }

    val initSelected = remember {
        ArrayList<Int>().apply {
            add(yearList.indexOf(curDate.year))
            if (showMonth) {
                add(monthList.indexOf(curDate.monthValue))
            }
            if (showDay) {
                add(dayIndex)
            }
        }
    }

    CommonMultiPicker(
        modifier = modifier,
        title = title,
        pickerList = pickerList,
        initSelected = initSelected,
        onClose = onClose,
        onConfirm = { selected ->
            val localDate = LocalDate.of(
                yearList[selected[0]],
                1,
                1
            )
                .run {
                    if (showMonth) {
                        withMonth(monthList[selected[1]]).run {
                            if (showDay) {
                                withDayOfMonth(dayList[selected[2]])
                            } else this@run
                        }
                    } else this
                }
            onConfirm(localDate)
        }
    )
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
