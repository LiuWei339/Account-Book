package com.wl.accountbook.ui.home

import com.wl.domain.model.MoneyRecordAndType
import java.util.Date

sealed class HomeAction {
    object ClickSearch: HomeAction()
    data class SelectDate(val timeStamp: Long): HomeAction()
//    object ClickBook: HomeAction()
    data class PressRecord(val record: MoneyRecordAndType): HomeAction()

}