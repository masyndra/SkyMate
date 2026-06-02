package ru.mirea.kt.ribo.skymate.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun observeSelectedDistrictId(): Flow<String?>

    suspend fun saveSelectedDistrictId(
        districtId: String
    )
}