package ru.mirea.kt.ribo.skymate.presentation.auth

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegisterSuccess: Boolean = false
)