package ru.mirea.kt.ribo.skymate.data.remote.dto

import ru.mirea.kt.ribo.skymate.domain.model.Weather

fun WeatherResponseDto.toWeather(): Weather {
    val currentWeather = current
        ?: throw IllegalStateException("Сервер не вернул текущие погодные данные")

    return Weather(
        temperature = currentWeather.temperature2m ?: 0.0,
        windSpeed = currentWeather.windSpeed10m ?: 0.0,
        precipitation = currentWeather.precipitation ?: 0.0,
        humidity = currentWeather.relativeHumidity2m,
        weatherCode = currentWeather.weatherCode,
        time = currentWeather.time.orEmpty()
    )
}