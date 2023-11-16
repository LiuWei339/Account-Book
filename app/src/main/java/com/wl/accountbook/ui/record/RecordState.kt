package com.wl.accountbook.ui.record

import com.wl.domain.model.MoneyRecordType
import java.util.Date

data class RecordState(
    val note: String = "",
    var date: Date = Date(),
    val typeIndexId: Int = -1,
    val tabIndex: Int = 0,
    val showDateSelector: Boolean = false,
    val recordTypes: List<MoneyRecordType> = emptyList(),
    val isValidRecord: Boolean = false
)