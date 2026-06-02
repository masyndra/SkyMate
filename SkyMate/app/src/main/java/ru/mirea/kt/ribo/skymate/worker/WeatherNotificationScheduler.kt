package ru.mirea.kt.ribo.skymate.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val WORK_NAME = "daily_weather_notification_work"
        private val NOTIFICATION_TIME: LocalTime = LocalTime.of(8, 0)
    }

    fun scheduleDailyWeatherNotification() {
        val delayMillis = calculateInitialDelayMillis()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<DailyWeatherWorker>(
            24,
            TimeUnit.HOURS
        )
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                30,
                TimeUnit.MINUTES
            )
            .build()

        WorkManager
            .getInstance(context)
            .enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
    }

    private fun calculateInitialDelayMillis(): Long {
        val now = LocalDateTime.now()

        var nextRun = now.withHour(NOTIFICATION_TIME.hour)
            .withMinute(NOTIFICATION_TIME.minute)
            .withSecond(0)
            .withNano(0)

        if (!nextRun.isAfter(now)) {
            nextRun = nextRun.plusDays(1)
        }

        return Duration.between(now, nextRun).toMillis()
    }
}