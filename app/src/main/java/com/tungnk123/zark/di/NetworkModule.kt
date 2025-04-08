package com.tungnk123.zark.di

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tungnk123.zark.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
                .addHeader(
                    "Content-Type",
                    "application/json"
                )
                .addHeader(
                    "Accept",
                    "application/json"
                )
                .addHeader(
                    "Authorization",
                    BuildConfig.ACCESS_TOKEN
                )
            chain.proceed(
                builder.build()
            )
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptorLevelBody(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request(
                String.format(
                    "%s-Request",
                    "Zark"
                )
            )
            .response(
                String.format(
                    "%s-Response",
                    "Zark"
                )
            )
            .build()
    }

    @Provides
    fun provideHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: LoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(null)
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(
                1,
                TimeUnit.MINUTES
            )
            .readTimeout(
                1,
                TimeUnit.MINUTES
            )
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.CHAT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }
}