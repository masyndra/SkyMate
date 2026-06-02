package ru.mirea.kt.ribo.skymate.domain.usecase.location

import ru.mirea.kt.ribo.skymate.domain.model.UserLocation
import ru.mirea.kt.ribo.skymate.domain.repository.LocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(): Result<UserLocation> {
        return locationRepository.getCurrentLocation()
    }
}