package ru.mirea.kt.ribo.skymate.data.repository

import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.repository.DistrictRepository
import ru.mirea.kt.ribo.skymate.util.DistrictDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DistrictRepositoryImpl @Inject constructor() : DistrictRepository {

    override fun getDistricts(): List<District> {
        return DistrictDataSource.districts
    }

    override fun getDistrictById(id: String): District? {
        return DistrictDataSource.districts.firstOrNull { district ->
            district.id == id
        }
    }
}