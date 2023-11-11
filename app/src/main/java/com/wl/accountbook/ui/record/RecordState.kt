package com.wl.accountbook.ui.record

import com.wl.domain.model.MoneyRecordType

data class RecordState(
    val note: String = "",
    val showTime: String = "",
    val typeIndexId: Int = -1,
    val tabIndex: Int = 0,
    val showDateSelector: Boolean = false,
    val recordTypes: List<MoneyRecordType> = emptyList(),
    val isValidRecord: Boolean = false
)