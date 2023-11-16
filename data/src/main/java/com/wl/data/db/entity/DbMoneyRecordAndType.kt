package com.wl.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.wl.domain.model.MoneyRecordAndType

data class DbMoneyRecordAndType(
    @Embedded val record: DbMoneyRecord,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "id"
    )
    val type: DbMoneyRecordType
) {
    fun toMoneyRecordAndType(): MoneyRecordAndType {
        return MoneyRecordAndType(
            amount = record.amount,
            type = type.toMoneyRecordType(),
            note = record.note,
            recordTime = record.recordTime,
            createTime = record.createTime
        )
    }
}