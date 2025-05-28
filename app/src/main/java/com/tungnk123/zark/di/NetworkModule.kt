package com.tungnk123.zark.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tungnk123.zark.BuildConfig
import com.tungnk123.zark.network.ConversationService
import com.tungnk123.zark.network.MessageService
import com.tungnk123.zark.network.ScheduleService
import com.tungnk123.zark.network.UserService
import com.tungnk123.zark.network.interceptor.AuthInterceptor
import com.tungnk123.zark.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_MINUTES = 1L
    private const val CACHE_SIZE = 50L * 1024 * 1024
    private const val MAX_STALE_CACHE_TIME = 60
    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, CACHE_SIZE)
    }

    @Provides
    @Singleton
    @AuthInterceptorAnnotation
    fun provideAuthInterceptor(
        tokenManager: TokenManager
    ) = AuthInterceptor(tokenManager)


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): LoggingInterceptor = LoggingInterceptor.Builder()
        .setLevel(Level.BASIC)
        .log(Platform.INFO)
        .request("Zark-Request")
        .response("Zark-Response")
        .build()

    @Provides
    @Singleton
    @ForceCacheInterceptorAnnotation
    fun provideForceCacheInterceptor(): Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        response.newBuilder()
            .header("Cache-Control", "public, only-if-cached, max-stale=$MAX_STALE_CACHE_TIME")
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context,
        @AuthInterceptorAnnotation authInterceptor: AuthInterceptor,
        loggingInterceptor: LoggingInterceptor,
        @ForceCacheInterceptorAnnotation forceCacheInterceptor: Interceptor,
        cache: Cache,
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(
            ChuckerInterceptor.Builder(context)
                .maxContentLength(10240)
                .build()
        )
        .connectTimeout(TIMEOUT_MINUTES, TimeUnit.MINUTES)
        .readTimeout(TIMEOUT_MINUTES, TimeUnit.MINUTES)
        .build()

    @Provides
    @Singleton
    @ChatRetrofit
    fun provideChatRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CHAT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    @ScheduleRetrofit
    fun provideScheduleRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SCHEDULE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(@ChatRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideMessageService(@ChatRetrofit retrofit: Retrofit): MessageService =
        retrofit.create(MessageService::class.java)

    @Provides
    @Singleton
    fun provideConversationService(@ChatRetrofit retrofit: Retrofit): ConversationService =
        retrofit.create(ConversationService::class.java)

    @Provides
    @Singleton
    fun provideScheduleService(@ScheduleRetrofit retrofit: Retrofit): ScheduleService =
        retrofit.create(ScheduleService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ForceCacheInterceptorAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChatRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ScheduleRetrofit
