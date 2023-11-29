package com.wl.data.repository

import com.wl.data.db.dao.RecordDao
import com.wl.data.db.dao.RecordTypeDao
import com.wl.data.db.entity.DbMoneyRecord
import com.wl.data.db.entity.DbMoneyRecordType
import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import com.wl.domain.repository.RecordRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecordRepoImpl @Inject constructor(
    private val recordDao: RecordDao,
    private val recordTypeDao: RecordTypeDao
) : RecordRepo {
    override fun getExpensesRecordTypes(): Flow<List<MoneyRecordType>> {
        return getRecordTypes(true)
    }

    override fun getIncomeRecordTypes(): Flow<List<MoneyRecordType>> {
        return getRecordTypes(false)
    }

    override fun getRecordTypes(isExpenses: Boolean): Flow<List<MoneyRecordType>> {
        return recordTypeDao.getRecordTypes(isExpenses).map { dbRecordTypes ->
            dbRecordTypes.map { it.toMoneyRecordType() }
                .sortedWith { o1, o2 ->
                    if (o1.isPreSet == o2.isPreSet) {
                        o1.id - o2.id
                    } else if (o1.isPreSet) {
                        -1
                    } else {
                        1
                    }
                }
        }
    }

    override fun getRecordTypes(): Flow<List<MoneyRecordType>> {
        return recordTypeDao.getAll().map { dbRecordTypes ->
            dbRecordTypes.map { it.toMoneyRecordType() }
                .sortedWith { o1, o2 ->
                    if (o1.isPreSet == o2.isPreSet) {
                        o1.id - o2.id
                    } else if (o1.isPreSet) {
                        -1
                    } else {
                        1
                    }
                }
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

    override suspend fun addOrUpdateTypes(recordTypes: List<MoneyRecordType>) {
        recordTypes.map { recordType ->
            DbMoneyRecordType(
                id = recordType.id,
                name = recordType.name,
                emoji = recordType.emoji,
                isExpenses = recordType.isExpenses,
                isPreSet = recordType.isPreSet
            )
        }.apply {
            recordTypeDao.insertOrUpdate(this)
        }
    }


    override suspend fun addOrUpdateRecord(moneyRecord: MoneyRecordAndType) {
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

    override fun getRecordAndTypes(start: Long, end: Long): Flow<List<MoneyRecordAndType>> {
        return recordDao.getRecordAndTypes(start, end).map { list ->
            list.map { it.toMoneyRecordAndType() }
        }
    }

    override suspend fun getRecordAndType(createTime: Long): MoneyRecordAndType {
        return recordDao.getRecordAndType(createTime).toMoneyRecordAndType()
    }

    override fun getSortedRecords(start: Long, end: Long): Flow<List<MoneyRecord>> {
        return recordDao.getSortedRecords(start, end).map { list ->
            list.map { it.toMoneyRecord() }
        }
    }

    override suspend fun deleteRecord(createTime: Long) {
        recordDao.delete(createTime)
    }

    override suspend fun searchRecordAndTypes(text: String): List<MoneyRecordAndType> {
        return recordDao.searchRecordsAndTypes(text).map { it.toMoneyRecordAndType() }
    }

    override suspend fun getRecordAndTypes(
        start: Long,
        end: Long,
        isExpenses: Boolean
    ): List<MoneyRecordAndType> {
        return recordDao.getRecordAndTypes(start, end, isExpenses).map { it.toMoneyRecordAndType() }
    }
}