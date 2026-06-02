package ru.mirea.kt.ribo.skymate.presentation.mainweather

import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.model.Weather

data class MainWeatherUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isDetectingLocation: Boolean = false,
    val district: District? = null,
    val weather: Weather? = null,
    val weatherAdvice: String = "",
    val weatherConditionText: String = "",
    val errorMessage: String? = null
)