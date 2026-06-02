package ru.mirea.kt.ribo.skymate.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.kt.ribo.skymate.domain.usecase.auth.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(value: String) {
        _uiState.update { state ->
            state.copy(
                username = value,
                errorMessage = null
            )
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update { state ->
            state.copy(
                email = value,
                errorMessage = null
            )
        }
    }

    fun onFullNameChange(value: String) {
        _uiState.update { state ->
            state.copy(
                fullName = value,
                errorMessage = null
            )
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { state ->
            state.copy(
                password = value,
                errorMessage = null
            )
        }
    }

    fun onRepeatPasswordChange(value: String) {
        _uiState.update { state ->
            state.copy(
                repeatPassword = value,
                errorMessage = null
            )
        }
    }

    fun register() {
        val state = _uiState.value

        if (state.password != state.repeatPassword) {
            _uiState.update {
                it.copy(
                    errorMessage = "Пароли не совпадают"
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    isRegisterSuccess = false
                )
            }

            val result = registerUseCase(
                username = state.username,
                email = state.email,
                fullName = state.fullName,
                password = state.password
            )

            result
                .onSuccess {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = null,
                            isRegisterSuccess = true
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Ошибка регистрации",
                            isRegisterSuccess = false
                        )
                    }
                }
        }
    }

    fun consumeRegisterSuccess() {
        _uiState.update { state ->
            state.copy(
                isRegisterSuccess = false
            )
        }
    }
}