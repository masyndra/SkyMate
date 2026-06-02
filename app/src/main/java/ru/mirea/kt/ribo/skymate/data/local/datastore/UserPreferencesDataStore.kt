package ru.mirea.kt.ribo.skymate.data.local.datastore

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.mirea.kt.ribo.skymate.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userPreferencesDataStore by preferencesDataStore(
    name = Constants.USER_PREFERENCES_DATASTORE_NAME
)

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object Keys {
        val SELECTED_DISTRICT_ID = stringPreferencesKey("selected_district_id")
    }

    val selectedDistrictIdFlow: Flow<String?> = context.userPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[Keys.SELECTED_DISTRICT_ID]
        }

    suspend fun saveSelectedDistrictId(
        districtId: String
    ) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[Keys.SELECTED_DISTRICT_ID] = districtId
        }
    }
}