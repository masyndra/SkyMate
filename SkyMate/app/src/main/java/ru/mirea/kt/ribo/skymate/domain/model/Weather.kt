package ru.mirea.kt.ribo.skymate.domain.model

data class Weather(
    val temperature: Double,
    val windSpeed: Double,
    val precipitation: Double,
    val humidity: Int?,
    val weatherCode: Int?,
    val time: String
)