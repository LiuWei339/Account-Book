package com.wl.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wl.data.db.entity.DbMoneyRecordType
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(recordType: DbMoneyRecordType)

    @Delete
    suspend fun delete(recordType: DbMoneyRecordType)

    @Query("SELECT * FROM DbMoneyRecordType")
    fun getAll(): Flow<List<DbMoneyRecordType>>

    @Query("SELECT * FROM DbMoneyRecordType WHERE id == :id")
    suspend fun getRecord(id: Int): DbMoneyRecordType

}