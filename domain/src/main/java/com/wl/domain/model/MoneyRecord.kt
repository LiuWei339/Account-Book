package com.wl.domain.model

data class MoneyRecord(
    val amount: Long,
    val typeId: Int,
    val note: String = "",
    val recordTime: Long,
    val createTime: Long = System.currentTimeMillis()
)
