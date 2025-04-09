package com.tungnk123.zark.di

import com.tungnk123.zark.repository.chat.ChatRepository
import com.tungnk123.zark.repository.chat.ChatRepositoryImpl
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
    fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}
