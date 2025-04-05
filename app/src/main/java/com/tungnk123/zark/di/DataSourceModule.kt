package com.tungnk123.zark.di

import com.tungnk123.zark.data.datasource.local.chat.LocalChatDataSource
import com.tungnk123.zark.data.datasource.local.chat.LocalChatDataSourceImpl
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
    fun bindLocalChatDataSource(localChatDataSource: LocalChatDataSourceImpl): LocalChatDataSource
}