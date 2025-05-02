package com.tungnk123.zark.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tungnk123.zark.data.dto.user.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "auth")

    companion object {
        val TOKEN_KEY = stringPreferencesKey("auth_token")
        val USER_ID = intPreferencesKey("user_id")
    }

    val token: Flow<String?> = context.dataStore.data
        .map { it[TOKEN_KEY] }

    val userId: Flow<Int?> = context.dataStore.data.map { it[USER_ID] }

    suspend fun saveLoginResponse(response: LoginResponse) {
        context.dataStore.edit { it[TOKEN_KEY] = response.token }
        context.dataStore.edit { it[USER_ID] = response.userId }
    }

    suspend fun clearLoginResponse() {
        context.dataStore.edit { it.remove(TOKEN_KEY) }
        context.dataStore.edit { it.remove(USER_ID) }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token}
    }

    suspend fun clearToken() {
        context.dataStore.edit { it.remove(TOKEN_KEY) }
    }

    suspend fun saveUserId(userId: Int) {
        context.dataStore.edit { it[USER_ID] = userId }
    }

    suspend fun clearUserId() {
        context.dataStore.edit { it.remove(USER_ID) }
    }
}
