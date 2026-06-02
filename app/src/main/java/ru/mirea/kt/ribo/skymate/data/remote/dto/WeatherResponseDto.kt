package ru.mirea.kt.ribo.skymate.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("latitude")
    val latitude: Double? = null,

    @SerializedName("longitude")
    val longitude: Double? = null,

    @SerializedName("generationtime_ms")
    val generationTimeMs: Double? = null,

    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int? = null,

    @SerializedName("timezone")
    val timezone: String? = null,

    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String? = null,

    @SerializedName("elevation")
    val elevation: Double? = null,

    @SerializedName("current_units")
    val currentUnits: CurrentUnitsDto? = null,

    @SerializedName("current")
    val current: CurrentWeatherDto? = null
)

data class CurrentUnitsDto(
    @SerializedName("time")
    val time: String? = null,

    @SerializedName("interval")
    val interval: String? = null,

    @SerializedName("temperature_2m")
    val temperature2m: String? = null,

    @SerializedName("relative_humidity_2m")
    val relativeHumidity2m: String? = null,

    @SerializedName("precipitation")
    val precipitation: String? = null,

    @SerializedName("weather_code")
    val weatherCode: String? = null,

    @SerializedName("wind_speed_10m")
    val windSpeed10m: String? = null
)

data class CurrentWeatherDto(
    @SerializedName("time")
    val time: String? = null,

    @SerializedName("interval")
    val interval: Int? = null,

    @SerializedName("temperature_2m")
    val temperature2m: Double? = null,

    @SerializedName("relative_humidity_2m")
    val relativeHumidity2m: Int? = null,

    @SerializedName("precipitation")
    val precipitation: Double? = null,

    @SerializedName("weather_code")
    val weatherCode: Int? = null,

    @SerializedName("wind_speed_10m")
    val windSpeed10m: Double? = null
)