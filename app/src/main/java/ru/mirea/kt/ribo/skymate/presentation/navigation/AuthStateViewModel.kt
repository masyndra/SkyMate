package ru.mirea.kt.ribo.skymate.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.mirea.kt.ribo.skymate.domain.usecase.auth.ObserveAuthStateUseCase
import javax.inject.Inject

@HiltViewModel
class AuthStateViewModel @Inject constructor(
    observeAuthStateUseCase: ObserveAuthStateUseCase
) : ViewModel() {

    val uiState = observeAuthStateUseCase()
        .map { isLoggedIn ->
            AuthStateUiState(
                isLoading = false,
                isLoggedIn = isLoggedIn
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AuthStateUiState()
        )
}

data class AuthStateUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false
)