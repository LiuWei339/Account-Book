package com.wl.domain.model

data class MoneyRecord(
    val amount: Long,
    val type: MoneyRecordType,
    val timeStamp: Long = System.currentTimeMillis(),
    val note: String
)
