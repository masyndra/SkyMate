package ru.mirea.kt.ribo.skymate.domain.usecase.district

import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.repository.DistrictRepository
import javax.inject.Inject

class GetDistrictsUseCase @Inject constructor(
    private val districtRepository: DistrictRepository
) {

    operator fun invoke(): List<District> {
        return districtRepository.getDistricts()
    }
}