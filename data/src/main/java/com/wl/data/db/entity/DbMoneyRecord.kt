package com.wl.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordAndType

@Entity
data class DbMoneyRecord(
    val amount: Long,
    val typeId: Int,
    val note: String = "",
    val recordTime: Long,
    @PrimaryKey val createTime: Long = System.currentTimeMillis()
) {
    fun toMoneyRecord(): MoneyRecord {
        return MoneyRecord(
            amount = amount,
            typeId = typeId,
            note = note,
            recordTime = recordTime,
            createTime = createTime
        )
    }
}
