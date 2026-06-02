package ru.mirea.kt.ribo.skymate.domain.repository

import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeatherByDistrict(
        district: District
    ): Result<Weather>
}