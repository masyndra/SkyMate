package ru.mirea.kt.ribo.skymate.data.location

import ru.mirea.kt.ribo.skymate.domain.model.UserLocation

interface LocationTracker {

    suspend fun getCurrentLocation(): Result<UserLocation>
}