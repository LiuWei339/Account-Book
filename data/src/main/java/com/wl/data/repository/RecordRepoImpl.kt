package com.wl.data.repository

import com.wl.data.db.dao.RecordDao
import com.wl.data.db.dao.RecordTypeDao
import com.wl.data.db.entity.DbMoneyRecord
import com.wl.data.db.entity.DbMoneyRecordType
import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordType
import com.wl.domain.repository.RecordRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class RecordRepoImpl @Inject constructor(
    private val recordDao: RecordDao,
    private val recordTypeDao: RecordTypeDao
): RecordRepo {
    override fun getExpensesRecordTypes(): Flow<List<MoneyRecordType>> {
        return getRecordTypes(true)
    }

    override fun getIncomeRecordTypes(): Flow<List<MoneyRecordType>> {
        return getRecordTypes(false)
    }

    override fun getRecordTypes(isExpenses: Boolean): Flow<List<MoneyRecordType>> {
        return recordTypeDao.getRecordTypes(isExpenses).map { dbRecordTypes ->
            dbRecordTypes.map { it.toMoneyRecordType() }
        }
    }

    override suspend fun addOrUpdateType(recordType: MoneyRecordType) {
        recordTypeDao.insertOrUpdate(
            DbMoneyRecordType(
                id = recordType.id,
                name = recordType.name,
                emoji = recordType.emoji,
                isExpenses = recordType.isExpenses,
                isPreSet = recordType.isPreSet
            )
        )
    }

    override suspend fun addOrUpdateRecord(moneyRecord: MoneyRecord) {
        recordDao.insertOrUpdate(
            moneyRecord.run {
                DbMoneyRecord(
                    amount = amount,
                    typeId = type.id,
                    note = note,
                    recordTime = recordTime,
                    createTime = createTime
                )
            }
        )
    }

    override fun getRecords(start: Long, end: Long): Flow<List<MoneyRecord>> {
        return recordDao.getRecordsAndType(start, end).map { list ->
            list.map { it.toMoneyRecord() }
        }
    }

}