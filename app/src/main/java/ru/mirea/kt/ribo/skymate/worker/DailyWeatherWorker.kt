package ru.mirea.kt.ribo.skymate.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import ru.mirea.kt.ribo.skymate.domain.usecase.district.GetDistrictByIdUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.preferences.GetSelectedDistrictIdUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.weather.GenerateWeatherAdviceUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.weather.GetWeatherByDistrictUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.weather.GetWeatherConditionTextUseCase
import ru.mirea.kt.ribo.skymate.util.Constants

@HiltWorker
class DailyWeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val getSelectedDistrictIdUseCase: GetSelectedDistrictIdUseCase,
    private val getDistrictByIdUseCase: GetDistrictByIdUseCase,
    private val getWeatherByDistrictUseCase: GetWeatherByDistrictUseCase,
    private val generateWeatherAdviceUseCase: GenerateWeatherAdviceUseCase,
    private val getWeatherConditionTextUseCase: GetWeatherConditionTextUseCase,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val selectedDistrictId = getSelectedDistrictIdUseCase().first()
            ?: Constants.DEFAULT_DISTRICT_ID

        val district = getDistrictByIdUseCase(selectedDistrictId)
            ?: getDistrictByIdUseCase(Constants.DEFAULT_DISTRICT_ID)
            ?: return Result.failure()

        val weatherResult = getWeatherByDistrictUseCase(district)

        return weatherResult.fold(
            onSuccess = { weather ->
                val advice = generateWeatherAdviceUseCase(weather)
                val conditionText = getWeatherConditionTextUseCase(weather.weatherCode)

                notificationHelper.showWeatherNotification(
                    district = district,
                    weather = weather,
                    advice = advice,
                    conditionText = conditionText
                )

                Result.success()
            },
            onFailure = {
                Result.retry()
            }
        )
    }
}