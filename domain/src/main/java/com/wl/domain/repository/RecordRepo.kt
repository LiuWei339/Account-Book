package com.wl.domain.repository

import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordType
import kotlinx.coroutines.flow.Flow

interface RecordRepo {
    // record types
    fun getExpensesRecordTypes(): Flow<List<MoneyRecordType>>
    fun getIncomeRecordTypes(): Flow<List<MoneyRecordType>>
    fun getRecordTypes(isExpenses: Boolean): Flow<List<MoneyRecordType>>
    suspend fun addOrUpdateType(recordType: MoneyRecordType)

    // record
    suspend fun addOrUpdateRecord(moneyRecord: MoneyRecord)
}