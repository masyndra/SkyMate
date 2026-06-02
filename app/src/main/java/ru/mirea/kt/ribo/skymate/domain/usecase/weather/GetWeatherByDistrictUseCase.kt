package ru.mirea.kt.ribo.skymate.domain.usecase.weather

import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.model.Weather
import ru.mirea.kt.ribo.skymate.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByDistrictUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        district: District
    ): Result<Weather> {
        return weatherRepository.getWeatherByDistrict(district)
    }
}