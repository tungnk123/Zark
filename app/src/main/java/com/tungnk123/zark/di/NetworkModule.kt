package com.tungnk123.zark.di

import android.content.Context
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tungnk123.zark.BuildConfig
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
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_MINUTES = 1L
    private const val CACHE_SIZE = 50L * 1024 * 1024
    private const val MAX_STALE_CACHE_TIME = 604800
    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, CACHE_SIZE)
    }

    @Provides
    @Singleton
    @HeaderInterceptor
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", BuildConfig.ACCESS_TOKEN)
            .build()
        chain.proceed(request)
    }

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
    @ForceCacheInterceptor
    fun provideForceCacheInterceptor(): Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        response.newBuilder()
            .header("Cache-Control", "public, only-if-cached, max-stale=$MAX_STALE_CACHE_TIME")
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        @HeaderInterceptor headerInterceptor: Interceptor,
        loggingInterceptor: LoggingInterceptor,
        @ForceCacheInterceptor forceCacheInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(forceCacheInterceptor)
        .connectTimeout(TIMEOUT_MINUTES, TimeUnit.MINUTES)
        .readTimeout(TIMEOUT_MINUTES, TimeUnit.MINUTES)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CHAT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ForceCacheInterceptor
