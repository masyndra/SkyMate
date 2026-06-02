package ru.mirea.kt.ribo.skymate.domain.repository

import ru.mirea.kt.ribo.skymate.domain.model.UserLocation

interface LocationRepository {

    suspend fun getCurrentLocation(): Result<UserLocation>
}