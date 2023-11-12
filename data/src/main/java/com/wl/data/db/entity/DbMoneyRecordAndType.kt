package com.wl.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.wl.domain.model.MoneyRecord

data class DbMoneyRecordAndType(
    @Embedded val record: DbMoneyRecord,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "id"
    )
    val type: DbMoneyRecordType
) {
    fun toMoneyRecord(): MoneyRecord {
        return MoneyRecord(
            amount = record.amount,
            type = type.toMoneyRecordType(),
            note = record.note,
            recordTime = record.recordTime,
            createTime = record.createTime
        )
    }
}