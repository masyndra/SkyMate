package ru.mirea.kt.ribo.skymate.data.repository

import kotlinx.coroutines.flow.Flow
import ru.mirea.kt.ribo.skymate.data.local.datastore.AuthDataStore
import ru.mirea.kt.ribo.skymate.data.local.room.dao.LocalUserDao
import ru.mirea.kt.ribo.skymate.data.local.room.entity.LocalUserEntity
import ru.mirea.kt.ribo.skymate.domain.model.User
import ru.mirea.kt.ribo.skymate.domain.repository.AuthRepository
import ru.mirea.kt.ribo.skymate.util.PasswordHasher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val localUserDao: LocalUserDao,
    private val authDataStore: AuthDataStore
) : AuthRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Result<User> {
        return try {
            val login = username.trim()

            if (login.isBlank()) {
                return Result.failure(Exception("Введите логин или email"))
            }

            if (password.isBlank()) {
                return Result.failure(Exception("Введите пароль"))
            }

            val userEntity = localUserDao.getUserByUsernameOrEmail(login)
                ?: return Result.failure(Exception("Пользователь не найден"))

            val passwordHash = PasswordHasher.hash(password)

            if (passwordHash != userEntity.passwordHash) {
                return Result.failure(Exception("Неверный пароль"))
            }

            val user = userEntity.toDomainUser()
            authDataStore.saveUser(user)

            Result.success(user)
        } catch (exception: Exception) {
            Result.failure(
                Exception(exception.message ?: "Не удалось выполнить вход")
            )
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        fullName: String,
        password: String
    ): Result<User> {
        return try {
            val cleanUsername = username.trim()
            val cleanEmail = email.trim()
            val cleanFullName = fullName.trim()

            if (cleanUsername.length < 3) {
                return Result.failure(Exception("Логин должен содержать минимум 3 символа"))
            }

            if (!cleanEmail.contains("@") || !cleanEmail.contains(".")) {
                return Result.failure(Exception("Введите корректный email"))
            }

            if (cleanFullName.length < 2) {
                return Result.failure(Exception("Введите имя пользователя"))
            }

            if (password.length < 6) {
                return Result.failure(Exception("Пароль должен содержать минимум 6 символов"))
            }

            if (localUserDao.countByUsername(cleanUsername) > 0) {
                return Result.failure(Exception("Пользователь с таким логином уже существует"))
            }

            if (localUserDao.countByEmail(cleanEmail) > 0) {
                return Result.failure(Exception("Пользователь с таким email уже существует"))
            }

            val entity = LocalUserEntity(
                username = cleanUsername,
                email = cleanEmail,
                fullName = cleanFullName,
                passwordHash = PasswordHasher.hash(password),
                createdAtMillis = System.currentTimeMillis()
            )

            localUserDao.insertUser(entity)

            val savedUserEntity = localUserDao.getUserByUsernameOrEmail(cleanUsername)
                ?: return Result.failure(Exception("Не удалось найти созданного пользователя"))

            val user = savedUserEntity.toDomainUser()
            authDataStore.saveUser(user)

            Result.success(user)
        } catch (exception: Exception) {
            Result.failure(
                Exception(exception.message ?: "Не удалось выполнить регистрацию")
            )
        }
    }

    override suspend fun logout() {
        authDataStore.clearAuthData()
    }

    override fun observeIsLoggedIn(): Flow<Boolean> {
        return authDataStore.isLoggedInFlow
    }

    override fun observeUsername(): Flow<String?> {
        return authDataStore.usernameFlow
    }
}

private fun LocalUserEntity.toDomainUser(): User {
    return User(
        id = id,
        username = username,
        fullName = fullName,
        email = email,
        accessToken = "local_token_${id}_${System.currentTimeMillis()}",
        refreshToken = "",
        imageUrl = ""
    )
}