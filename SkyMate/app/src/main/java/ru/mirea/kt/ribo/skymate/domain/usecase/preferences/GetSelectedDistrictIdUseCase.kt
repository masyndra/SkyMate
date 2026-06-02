package ru.mirea.kt.ribo.skymate.domain.usecase.preferences

import kotlinx.coroutines.flow.Flow
import ru.mirea.kt.ribo.skymate.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class GetSelectedDistrictIdUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(): Flow<String?> {
        return userPreferencesRepository.observeSelectedDistrictId()
    }
}