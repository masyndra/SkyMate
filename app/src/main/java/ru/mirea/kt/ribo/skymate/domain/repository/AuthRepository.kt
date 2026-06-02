package ru.mirea.kt.ribo.skymate.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.mirea.kt.ribo.skymate.domain.model.User

interface AuthRepository {

    suspend fun login(
        username: String,
        password: String
    ): Result<User>

    suspend fun register(
        username: String,
        email: String,
        fullName: String,
        password: String
    ): Result<User>

    suspend fun logout()

    fun observeIsLoggedIn(): Flow<Boolean>

    fun observeUsername(): Flow<String?>

}