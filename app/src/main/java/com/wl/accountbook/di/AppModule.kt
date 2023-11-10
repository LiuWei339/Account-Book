package com.wl.accountbook.di

import android.app.Application
import androidx.room.Room
import com.wl.data.db.AccountBookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAccountBookDatabase(
        application: Application
    ): AccountBookDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = AccountBookDatabase::class.java,
            name = "account_book_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    fun provideRecordDao(db: AccountBookDatabase) = db.recordDao()

    fun provideRecordTypeDao(db: AccountBookDatabase) = db.recordTypeDao()

}