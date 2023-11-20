package com.wl.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wl.domain.model.MoneyRecordType

@Entity
data class DbMoneyRecordType(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val emoji: String,
    val isExpenses: Boolean,
    val isPreSet: Boolean = false
) {
    fun toMoneyRecordType(): MoneyRecordType {
        return MoneyRecordType(
            id, name, emoji, isExpenses, isPreSet
        )
    }
}
