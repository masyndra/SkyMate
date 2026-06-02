package ru.mirea.kt.ribo.skymate.presentation.districtlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.kt.ribo.skymate.domain.usecase.district.GetDistrictsUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.preferences.GetSelectedDistrictIdUseCase
import ru.mirea.kt.ribo.skymate.domain.usecase.preferences.SaveSelectedDistrictIdUseCase
import javax.inject.Inject

@HiltViewModel
class DistrictListViewModel @Inject constructor(
    private val getDistrictsUseCase: GetDistrictsUseCase,
    private val getSelectedDistrictIdUseCase: GetSelectedDistrictIdUseCase,
    private val saveSelectedDistrictIdUseCase: SaveSelectedDistrictIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DistrictListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDistricts()
        observeSelectedDistrict()
    }

    private fun loadDistricts() {
        _uiState.update { state ->
            state.copy(
                districts = getDistrictsUseCase()
            )
        }
    }

    private fun observeSelectedDistrict() {
        viewModelScope.launch {
            getSelectedDistrictIdUseCase().collect { districtId ->
                _uiState.update { state ->
                    state.copy(
                        selectedDistrictId = districtId
                    )
                }
            }
        }
    }

    fun selectDistrict(
        districtId: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            saveSelectedDistrictIdUseCase(districtId)
            onSuccess()
        }
    }
}