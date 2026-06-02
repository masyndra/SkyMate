package ru.mirea.kt.ribo.skymate.domain.usecase.auth

import ru.mirea.kt.ribo.skymate.domain.model.User
import ru.mirea.kt.ribo.skymate.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        username: String,
        password: String
    ): Result<User> {
        return authRepository.login(
            username = username,
            password = password
        )
    }
}