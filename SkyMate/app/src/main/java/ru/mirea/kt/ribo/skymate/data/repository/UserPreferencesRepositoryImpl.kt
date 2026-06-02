package ru.mirea.kt.ribo.skymate.data.repository

import kotlinx.coroutines.flow.Flow
import ru.mirea.kt.ribo.skymate.data.local.datastore.UserPreferencesDataStore
import ru.mirea.kt.ribo.skymate.domain.repository.UserPreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : UserPreferencesRepository {

    override fun observeSelectedDistrictId(): Flow<String?> {
        return userPreferencesDataStore.selectedDistrictIdFlow
    }

    override suspend fun saveSelectedDistrictId(
        districtId: String
    ) {
        userPreferencesDataStore.saveSelectedDistrictId(districtId)
    }
}