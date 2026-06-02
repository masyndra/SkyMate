package ru.mirea.kt.ribo.skymate.presentation.districtlist

import ru.mirea.kt.ribo.skymate.domain.model.District

data class DistrictListUiState(
    val districts: List<District> = emptyList(),
    val selectedDistrictId: String? = null
)