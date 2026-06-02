package ru.mirea.kt.ribo.skymate.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import ru.mirea.kt.ribo.skymate.domain.repository.AuthRepository
import javax.inject.Inject

class ObserveAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return authRepository.observeIsLoggedIn()
    }
}