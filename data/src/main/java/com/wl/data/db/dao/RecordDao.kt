package com.wl.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.wl.data.db.entity.DbMoneyRecord
import com.wl.data.db.entity.DbMoneyRecordAndType
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query("SELECT * FROM DbMoneyRecord")
    fun getAll(): Flow<List<DbMoneyRecord>>

    @Query("SELECT * FROM DbMoneyRecord WHERE recordTime <= :end AND recordTime >= :start")
    fun getRecords(start: Long, end: Long): Flow<List<DbMoneyRecord>>

    @Query("SELECT * FROM DbMoneyRecord WHERE recordTime <= :end AND recordTime >= :start ORDER BY recordTime DESC, createTime DESC")
    fun getSortedRecords(start: Long, end: Long): Flow<List<DbMoneyRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: DbMoneyRecord)

    @Delete
    suspend fun delete(record: DbMoneyRecord)

    @Query("DELETE FROM DbMoneyRecord WHERE createTime = :createTime")
    suspend fun delete(createTime: Long)

    @Transaction
    @Query("SELECT * FROM DbMoneyRecord WHERE recordTime <= :end AND recordTime >= :start " +
            "ORDER BY recordTime DESC, createTime DESC")
    fun getRecordsAndTypes(start: Long, end: Long): Flow<List<DbMoneyRecordAndType>>

    @Transaction
    @Query("SELECT * FROM DbMoneyRecord WHERE createTime = :createTime")
    suspend fun getRecordAndType(createTime: Long): DbMoneyRecordAndType

    @Transaction
    @Query("SELECT r.* FROM DbMoneyRecord as r " +
            "INNER JOIN DbMoneyRecordType as t ON r.typeId == t.id " +
            "WHERE r.note LIKE '%' || :text || '%' OR t.name LIKE '%' || :text || '%' " +
            "ORDER BY r.recordTime DESC, r.createTime DESC")
    suspend fun searchRecordsAndTypes(text: String): List<DbMoneyRecordAndType>
}