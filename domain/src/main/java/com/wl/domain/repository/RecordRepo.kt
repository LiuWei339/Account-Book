package com.wl.domain.repository

import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import kotlinx.coroutines.flow.Flow

interface RecordRepo {
    // record types
    fun getExpensesRecordTypes(): Flow<List<MoneyRecordType>>
    fun getIncomeRecordTypes(): Flow<List<MoneyRecordType>>
    fun getRecordTypes(isExpenses: Boolean): Flow<List<MoneyRecordType>>
    fun getRecordTypes(): Flow<List<MoneyRecordType>>
    suspend fun addOrUpdateType(recordType: MoneyRecordType)

    // record and type
    suspend fun addOrUpdateRecord(moneyRecord: MoneyRecordAndType)
    fun getRecordAndTypes(start: Long, end: Long): Flow<List<MoneyRecordAndType>>
    suspend fun getRecordAndType(createTime: Long): MoneyRecordAndType

    // record
    fun getSortedRecords(start: Long, end: Long): Flow<List<MoneyRecord>>
    suspend fun deleteRecord(createTime: Long)

    // search
    suspend fun searchRecordAndTypes(text: String): List<MoneyRecordAndType>
}