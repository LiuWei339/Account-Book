package com.wl.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wl.data.db.dao.RecordDao
import com.wl.data.db.dao.RecordTypeDao
import com.wl.data.db.entity.DbMoneyRecord
import com.wl.data.db.entity.DbMoneyRecordType

@Database(
    entities = [DbMoneyRecord::class, DbMoneyRecordType::class],
    version = 1,
    exportSchema = false
)
abstract class AccountBookDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
    abstract fun recordTypeDao(): RecordTypeDao
}