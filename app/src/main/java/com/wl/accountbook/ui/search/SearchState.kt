package com.wl.accountbook.ui.search

import com.wl.domain.model.MoneyRecordAndType

data class SearchState(
    val searchText: String,
    val records: List<MoneyRecordAndType>
)
