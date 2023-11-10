package com.wl.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.wl.data.db.entity.DbMoneyRecord
import com.wl.data.db.entity.DbMoneyWithRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query("SELECT * FROM DbMoneyRecord")
    fun getAll(): Flow<List<DbMoneyRecord>>

    @Query("SELECT * FROM DbMoneyRecord WHERE recordTime <= :end AND recordTime >= :start")
    fun getRecords(start: Long, end: Long): Flow<List<DbMoneyRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: DbMoneyRecord)

    @Delete
    suspend fun delete(record: DbMoneyRecord)

    @Transaction
    @Query("SELECT * FROM DbMoneyRecord WHERE recordTime <= :end AND recordTime >= :start")
    fun getRecordsWithType(start: Long, end: Long): Flow<List<DbMoneyWithRecord>>
}