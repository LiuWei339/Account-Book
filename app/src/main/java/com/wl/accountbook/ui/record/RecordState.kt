package com.wl.accountbook.ui.record

import com.wl.domain.model.MoneyRecordType

data class RecordState(
    val number: String,
    val note: String,
    val timeStamp: Long,
    val showTime: String = "",
    val typeIndexId: Int = -1,
    val tabIndex: Int = 0,
    val recordTypes: List<MoneyRecordType> = emptyList()
)