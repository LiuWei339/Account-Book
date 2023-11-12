package com.wl.domain.repository

import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordType
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface RecordRepo {
    // record types
    fun getExpensesRecordTypes(): Flow<List<MoneyRecordType>>
    fun getIncomeRecordTypes(): Flow<List<MoneyRecordType>>
    fun getRecordTypes(isExpenses: Boolean): Flow<List<MoneyRecordType>>
    suspend fun addOrUpdateType(recordType: MoneyRecordType)

    // record
    suspend fun addOrUpdateRecord(moneyRecord: MoneyRecord)
    fun getRecords(start: Long, end: Long): Flow<List<MoneyRecord>>
}