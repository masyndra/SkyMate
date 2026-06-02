package ru.mirea.kt.ribo.skymate.data.repository

import ru.mirea.kt.ribo.skymate.data.location.LocationTracker
import ru.mirea.kt.ribo.skymate.domain.model.UserLocation
import ru.mirea.kt.ribo.skymate.domain.repository.LocationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val locationTracker: LocationTracker
) : LocationRepository {

    override suspend fun getCurrentLocation(): Result<UserLocation> {
        return locationTracker.getCurrentLocation()
    }
}