package ru.mirea.kt.ribo.skymate.domain.usecase.auth

import ru.mirea.kt.ribo.skymate.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() {
        authRepository.logout()
    }
}