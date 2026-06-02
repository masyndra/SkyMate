package ru.mirea.kt.ribo.skymate.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.mirea.kt.ribo.skymate.data.remote.dto.WeatherResponseDto
import ru.mirea.kt.ribo.skymate.util.Constants

interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = Constants.WEATHER_CURRENT_PARAMETERS,
        @Query("timezone") timezone: String = Constants.WEATHER_TIMEZONE
    ): WeatherResponseDto
}