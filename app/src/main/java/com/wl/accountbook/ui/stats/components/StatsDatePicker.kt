package com.wl.accountbook.ui.stats.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wl.accountbook.R
import com.wl.accountbook.ui.common.components.CommonMultiPicker
import java.time.LocalDate

@Composable
fun StatsDatePicker(
    modifier: Modifier = Modifier,
    curDate: LocalDate = LocalDate.now(),
    onClose: () -> Unit,
    onConfirm: (LocalDate, isSelectYear: Boolean) -> Unit
) {
    val yearList = remember(curDate.year) { (2020..LocalDate.now().year).toList() }
    val monthList = remember { (0..12).toList() }

    var pickerList = remember {
        listOf(yearList.map { it.toString() }, monthList.map { it.toString() })
    }
    val initSelected = remember {
        listOf(yearList.indexOf(curDate.year), monthList.indexOf(curDate.monthValue))
    }

    CommonMultiPicker(
        modifier = modifier,
        title = stringResource(id = R.string.select_year_or_month),
        pickerList = pickerList,
        initSelected = initSelected,
        onClose = onClose,
        onConfirm = { selected ->
            val isSelectYear = monthList[selected[1]] == 0
            val localDate = LocalDate.of(
                yearList[selected[0]],
                if (isSelectYear) 1 else monthList[selected[1]],
                1
            )
            onConfirm(localDate, isSelectYear)
        }
    )
}