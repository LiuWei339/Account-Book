package com.wl.accountbook.ui.home

import com.wl.domain.model.MoneyRecordAndType

sealed class HomeAction {
    object ClickSearch: HomeAction()
    object ClickDate: HomeAction()
//    object ClickBook: HomeAction()
    data class PressRecord(val record: MoneyRecordAndType): HomeAction()

}