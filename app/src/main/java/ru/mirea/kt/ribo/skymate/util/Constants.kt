package ru.mirea.kt.ribo.skymate.util

object Constants {

    const val WEATHER_BASE_URL = "https://api.open-meteo.com/"

    const val DATABASE_NAME = "skymate_database"

    const val WEATHER_CURRENT_PARAMETERS =
        "temperature_2m,relative_humidity_2m,precipitation,weather_code,wind_speed_10m"

    const val WEATHER_TIMEZONE = "Europe/Moscow"

    const val AUTH_DATASTORE_NAME = "auth_preferences"

    const val USER_PREFERENCES_DATASTORE_NAME = "user_preferences"

    const val DEFAULT_DISTRICT_ID = "cao"
}