package com.wl.accountbook.di

import com.wl.data.manager.LocalUserManagerImpl
import com.wl.domain.manager.LocalUserManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindsModule {

    @Binds
    abstract fun bindLocalUserManager(localUserManager: LocalUserManagerImpl): LocalUserManager
}