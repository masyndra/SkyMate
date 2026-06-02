package ru.mirea.kt.ribo.skymate.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.kt.ribo.skymate.domain.usecase.auth.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.update { state ->
            state.copy(
                username = username,
                errorMessage = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { state ->
            state.copy(
                password = password,
                errorMessage = null
            )
        }
    }

    fun login() {
        val currentState = _uiState.value

        if (currentState.username.isBlank()) {
            _uiState.update { state ->
                state.copy(
                    errorMessage = "Введите логин"
                )
            }
            return
        }

        if (currentState.password.isBlank()) {
            _uiState.update { state ->
                state.copy(
                    errorMessage = "Введите пароль"
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true,
                    errorMessage = null,
                    isLoginSuccess = false
                )
            }

            val result = loginUseCase(
                username = currentState.username,
                password = currentState.password
            )

            result
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = null,
                            isLoginSuccess = true
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Ошибка входа",
                            isLoginSuccess = false
                        )
                    }
                }
        }
    }

    fun consumeLoginSuccess() {
        _uiState.update { state ->
            state.copy(
                isLoginSuccess = false
            )
        }
    }
}