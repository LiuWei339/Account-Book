package com.wl.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbMoneyRecord(
    val amount: Long,
    val typeId: Int,
    val note: String = "",
    val recordTime: Long,
    @PrimaryKey val createTime: Long = System.currentTimeMillis()
)
