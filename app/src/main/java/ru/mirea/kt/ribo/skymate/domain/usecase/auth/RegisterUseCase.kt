package ru.mirea.kt.ribo.skymate.domain.usecase.auth

import ru.mirea.kt.ribo.skymate.domain.model.User
import ru.mirea.kt.ribo.skymate.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        username: String,
        email: String,
        fullName: String,
        password: String
    ): Result<User> {
        return authRepository.register(
            username = username,
            email = email,
            fullName = fullName,
            password = password
        )
    }
}