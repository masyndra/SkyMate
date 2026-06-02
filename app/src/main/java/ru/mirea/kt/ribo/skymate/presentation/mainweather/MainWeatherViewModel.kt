package ru.mirea.kt.ribo.skymate.presentation.mainweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.usecase.auth.LogoutUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.district.GetDistrictByIdUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.location.DetectNearestDistrictUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.location.GetCurrentLocationUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.preferences.GetSelectedDistrictIdUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.preferences.SaveSelectedDistrictIdUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.weather.GenerateWeatherAdviceUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.weather.GetWeatherByDistrictUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.weather.GetWeatherConditionTextUseCase
import ru.mirea.kt.ribo.skymate.worker.WeatherNotificationScheduler
import javax.inject.Inject

@HiltViewModel
class MainWeatherViewModel @Inject constructor(
    private val getSelectedDistrictIdUseCase: GetSelectedDistrictIdUseCase,
    private val saveSelectedDistrictIdUseCase: SaveSelectedDistrictIdUseCase,
    private val getDistrictByIdUseCase: GetDistrictByIdUseCase,
    private val getWeatherByDistrictUseCase: GetWeatherByDistrictUseCase,
    private val generateWeatherAdviceUseCase: GenerateWeatherAdviceUseCase,
    private val getWeatherConditionTextUseCase: GetWeatherConditionTextUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val detectNearestDistrictUseCase: DetectNearestDistrictUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val weatherNotificationScheduler: WeatherNotificationScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainWeatherUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeSelectedDistrict()
        weatherNotificationScheduler.scheduleDailyWeatherNotification()
    }

    private fun observeSelectedDistrict() {
        viewModelScope.launch {
            getSelectedDistrictIdUseCase().collect { districtId ->
                if (districtId.isNullOrBlank()) {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isRefreshing = false,
                            district = null,
                            weather = null,
                            weatherAdvice = "",
                            weatherConditionText = "",
                            errorMessage = "Основной район не выбран. Выберите район вручную или определите его по GPS."
                        )
                    }
                } else {
                    val district = getDistrictByIdUseCase(districtId)

                    if (district == null) {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                isRefreshing = false,
                                district = null,
                                weather = null,
                                weatherAdvice = "",
                                weatherConditionText = "",
                                errorMessage = "Сохранённый район не найден"
                            )
                        }
                    } else {
                        loadWeather(
                            district = district,
                            clearPreviousWeather = true
                        )
                    }
                }
            }
        }
    }

    fun detectDistrictByLocation() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isDetectingLocation = true,
                    errorMessage = null
                )
            }

            val locationResult = getCurrentLocationUseCase()

            locationResult
                .onSuccess { userLocation ->
                    val nearestDistrict = detectNearestDistrictUseCase(userLocation)

                    if (nearestDistrict == null) {
                        _uiState.update { state ->
                            state.copy(
                                isDetectingLocation = false,
                                errorMessage = "Не удалось определить ближайший район"
                            )
                        }
                    } else {
                        saveSelectedDistrictIdUseCase(nearestDistrict.id)

                        _uiState.update { state ->
                            state.copy(
                                isDetectingLocation = false,
                                errorMessage = null
                            )
                        }
                    }
                }
                .onFailure { exception ->
                    _uiState.update { state ->
                        state.copy(
                            isDetectingLocation = false,
                            errorMessage = exception.message ?: "Не удалось определить район по GPS"
                        )
                    }
                }
        }
    }

    fun onLocationPermissionDenied() {
        _uiState.update { state ->
            state.copy(
                isDetectingLocation = false,
                errorMessage = "Разрешение на геолокацию не выдано. Выберите район вручную."
            )
        }
    }

    fun refreshWeather() {
        val district = _uiState.value.district ?: return

        loadWeather(
            district = district,
            clearPreviousWeather = false
        )
    }

    private fun loadWeather(
        district: District,
        clearPreviousWeather: Boolean
    ) {
        _uiState.update { state ->
            state.copy(
                isLoading = clearPreviousWeather,
                isRefreshing = !clearPreviousWeather,
                district = district,
                weather = if (clearPreviousWeather) null else state.weather,
                weatherAdvice = if (clearPreviousWeather) "" else state.weatherAdvice,
                weatherConditionText = if (clearPreviousWeather) "" else state.weatherConditionText,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            val result = getWeatherByDistrictUseCase(district)

            result
                .onSuccess { weather ->
                    val advice = generateWeatherAdviceUseCase(weather)
                    val conditionText = getWeatherConditionTextUseCase(weather.weatherCode)

                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isRefreshing = false,
                            district = district,
                            weather = weather,
                            weatherAdvice = advice,
                            weatherConditionText = conditionText,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isRefreshing = false,
                            district = district,
                            errorMessage = exception.message ?: "Не удалось загрузить погоду"
                        )
                    }
                }
        }
    }

    fun logout(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            logoutUseCase()
            onSuccess()
        }
    }
}