package com.tungnk123.zark.di

import com.tungnk123.zark.repository.conversation.ConversationRepository
import com.tungnk123.zark.repository.conversation.ConversationRepositoryImpl
import com.tungnk123.zark.repository.message.MessageRepository
import com.tungnk123.zark.repository.message.MessageRepositoryImpl
import com.tungnk123.zark.repository.schedule.ScheduleRepository
import com.tungnk123.zark.repository.schedule.ScheduleRepositoryImpl
import com.tungnk123.zark.repository.user.UserRepository
import com.tungnk123.zark.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindMessageRepository(messageRepository: MessageRepositoryImpl): MessageRepository

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindConversationRepository(conversationRepositoryImpl: ConversationRepositoryImpl): ConversationRepository

    @Binds
    @Singleton
    fun bindScheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl): ScheduleRepository

}
