package ru.mirea.kt.ribo.skymate.domain.repository

import ru.mirea.kt.ribo.skymate.domain.model.District

interface DistrictRepository {

    fun getDistricts(): List<District>

    fun getDistrictById(id: String): District?
}