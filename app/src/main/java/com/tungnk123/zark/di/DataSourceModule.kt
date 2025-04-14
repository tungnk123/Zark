package com.tungnk123.zark.di

import com.tungnk123.zark.data.datasource.chat.LocalMessageDataSourceImpl
import com.tungnk123.zark.data.datasource.chat.MessageDataSource
import com.tungnk123.zark.data.datasource.conversation.ConversationDataSource
import com.tungnk123.zark.data.datasource.conversation.ConversationDataSourceImpl
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
    fun bindLocalChatDataSource(localChatDataSource: LocalMessageDataSourceImpl): MessageDataSource

    @Binds
    @Singleton
    fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource

    @Binds
    @Singleton
    fun bindConversationDataSource(conversationDataSourceImpl: ConversationDataSourceImpl): ConversationDataSource
}