package ru.mirea.kt.ribo.skymate.presentation.navigation

sealed class Screen(
    val route: String
) {
    data object Login : Screen("login")

    data object Register : Screen("register")

    data object MainWeather : Screen("main_weather")

    data object DistrictList : Screen("district_list")
}