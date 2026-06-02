package ru.mirea.kt.ribo.skymate.domain.usecase.location

import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.model.UserLocation
import ru.mirea.kt.ribo.skymate.domain.repository.DistrictRepository
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import javax.inject.Inject

class DetectNearestDistrictUseCase @Inject constructor(
    private val districtRepository: DistrictRepository
) {

    operator fun invoke(
        userLocation: UserLocation
    ): District? {
        return districtRepository.getDistricts().minByOrNull { district ->
            calculateDistanceMeters(
                firstLatitude = userLocation.latitude,
                firstLongitude = userLocation.longitude,
                secondLatitude = district.latitude,
                secondLongitude = district.longitude
            )
        }
    }

    private fun calculateDistanceMeters(
        firstLatitude: Double,
        firstLongitude: Double,
        secondLatitude: Double,
        secondLongitude: Double
    ): Double {
        val earthRadiusMeters = 6_371_000.0

        val firstLatitudeRadians = Math.toRadians(firstLatitude)
        val secondLatitudeRadians = Math.toRadians(secondLatitude)

        val latitudeDelta = Math.toRadians(secondLatitude - firstLatitude)
        val longitudeDelta = Math.toRadians(secondLongitude - firstLongitude)

        val haversine = sin(latitudeDelta / 2).pow(2.0) +
                cos(firstLatitudeRadians) *
                cos(secondLatitudeRadians) *
                sin(longitudeDelta / 2).pow(2.0)

        val angularDistance = 2 * atan2(
            sqrt(haversine),
            sqrt(1 - haversine)
        )

        return earthRadiusMeters * angularDistance
    }
}