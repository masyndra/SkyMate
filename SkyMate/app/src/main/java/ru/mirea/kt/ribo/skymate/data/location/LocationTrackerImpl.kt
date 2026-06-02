package ru.mirea.kt.ribo.skymate.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import ru.mirea.kt.ribo.skymate.domain.model.UserLocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationTrackerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationTracker {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Result<UserLocation> {
        return try {
            if (!hasLocationPermission()) {
                return Result.failure(
                    Exception("Нет разрешения на доступ к геолокации")
                )
            }

            val lastLocation = fusedLocationProviderClient.lastLocation.await()

            if (lastLocation != null) {
                return Result.success(
                    UserLocation(
                        latitude = lastLocation.latitude,
                        longitude = lastLocation.longitude
                    )
                )
            }

            val request = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                .build()

            val currentLocation = fusedLocationProviderClient
                .getCurrentLocation(request, null)
                .await()

            if (currentLocation == null) {
                Result.failure(
                    Exception("Не удалось получить текущую геолокацию. Проверьте, включена ли геолокация на устройстве.")
                )
            } else {
                Result.success(
                    UserLocation(
                        latitude = currentLocation.latitude,
                        longitude = currentLocation.longitude
                    )
                )
            }
        } catch (exception: Exception) {
            Result.failure(
                Exception(exception.message ?: "Ошибка при получении геолокации")
            )
        }
    }

    private fun hasLocationPermission(): Boolean {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return hasFineLocation || hasCoarseLocation
    }
}