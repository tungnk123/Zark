package com.tungnk123.zark.di

import com.tungnk123.zark.data.datasource.chat.ChatDataSource
import com.tungnk123.zark.data.datasource.chat.LocalChatDataSourceImpl
import com.tungnk123.zark.data.datasource.user.UserDataSource
import com.tungnk123.zark.data.datasource.user.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindLocalChatDataSource(localChatDataSource: LocalChatDataSourceImpl): ChatDataSource

    @Binds
    @Singleton
    fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource
}