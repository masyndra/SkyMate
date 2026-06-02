package ru.mirea.kt.ribo.skymate.domain.usecase.preferences

import ru.mirea.kt.ribo.skymate.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveSelectedDistrictIdUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(
        districtId: String
    ) {
        userPreferencesRepository.saveSelectedDistrictId(districtId)
    }
}