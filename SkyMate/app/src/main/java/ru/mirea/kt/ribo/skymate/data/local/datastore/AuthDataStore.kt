package ru.mirea.kt.ribo.skymate.data.local.datastore

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.mirea.kt.ribo.skymate.domain.model.User
import ru.mirea.kt.ribo.skymate.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

private val Context.authDataStore by preferencesDataStore(
    name = Constants.AUTH_DATASTORE_NAME
)

@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object Keys {
        val USER_ID = intPreferencesKey("user_id")
        val USERNAME = stringPreferencesKey("username")
        val FULL_NAME = stringPreferencesKey("full_name")
        val EMAIL = stringPreferencesKey("email")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val IMAGE_URL = stringPreferencesKey("image_url")
    }

    val accessTokenFlow: Flow<String?> = context.authDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[Keys.ACCESS_TOKEN]
        }

    val usernameFlow: Flow<String?> = context.authDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[Keys.USERNAME]
        }

    val isLoggedInFlow: Flow<Boolean> = accessTokenFlow.map { token ->
        !token.isNullOrBlank()
    }

    suspend fun saveUser(user: User) {
        context.authDataStore.edit { preferences ->
            preferences[Keys.USER_ID] = user.id
            preferences[Keys.USERNAME] = user.username
            preferences[Keys.FULL_NAME] = user.fullName
            preferences[Keys.EMAIL] = user.email
            preferences[Keys.ACCESS_TOKEN] = user.accessToken
            preferences[Keys.REFRESH_TOKEN] = user.refreshToken
            preferences[Keys.IMAGE_URL] = user.imageUrl
        }
    }

    suspend fun clearAuthData() {
        context.authDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}