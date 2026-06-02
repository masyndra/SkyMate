package ru.mirea.kt.ribo.skymate.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mirea.kt.ribo.skymate.presentation.auth.LoginScreen
import ru.mirea.kt.ribo.skymate.presentation.auth.RegisterScreen
import ru.mirea.kt.ribo.skymate.presentation.districtlist.DistrictListScreen
import ru.mirea.kt.ribo.skymate.presentation.mainweather.MainWeatherScreen

@Composable
fun AppNavGraph(
    authStateViewModel: AuthStateViewModel = hiltViewModel()
) {
    val authState by authStateViewModel.uiState.collectAsState()

    if (authState.isLoading) {
        LoadingScreen()
        return
    }

    val navController = rememberNavController()

    val startDestination = if (authState.isLoggedIn) {
        Screen.MainWeather.route
    } else {
        Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.MainWeather.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.MainWeather.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.MainWeather.route) {
            MainWeatherScreen(
                onOpenDistrictList = {
                    navController.navigate(Screen.DistrictList.route)
                },
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.MainWeather.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.DistrictList.route) {
            DistrictListScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Загрузка...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}