package com.wl.domain.model

data class MoneyRecord(
    val amount: Long,
    val typeId: Int,
    val note: String = "",
    val recordTime: Long, // should be the start of the day
    val createTime: Long = System.currentTimeMillis()
)
