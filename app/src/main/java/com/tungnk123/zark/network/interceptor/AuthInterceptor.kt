package com.tungnk123.zark.network.interceptor

import com.tungnk123.zark.utils.TokenManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token = runBlocking {
            tokenManager.token.firstOrNull()
        }

        token?.let {
            requestBuilder.addHeader("Authorization", it)
        }

        requestBuilder.addHeader("Accept", "*/*")

        return chain.proceed(requestBuilder.build())
    }
}

