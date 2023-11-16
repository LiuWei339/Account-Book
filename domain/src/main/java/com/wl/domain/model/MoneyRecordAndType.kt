package com.wl.domain.model

data class MoneyRecordAndType(
    val amount: Long,
    val type: MoneyRecordType,
    val note: String = "",
    val recordTime: Long,
    val createTime: Long = System.currentTimeMillis()
) {
    constructor(record: MoneyRecord, type: MoneyRecordType) : this(
        record.amount,
        type,
        record.note,
        record.recordTime,
        record.createTime
    )
}
