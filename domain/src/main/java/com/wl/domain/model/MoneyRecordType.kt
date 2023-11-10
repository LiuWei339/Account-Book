package com.wl.domain.model

data class MoneyRecordType(
    val id: Int,
    val name: String,
    val emoji: String,
    val isExpenses: Boolean,
    val isPreSet: Boolean = false
)
