package ru.mirea.kt.ribo.skymate.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import ru.mirea.kt.ribo.skymate.domain.model.Weather
import ru.mirea.kt.ribo.skymate.presentation.theme.SkyMateTheme

class WeatherInfoCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun weatherInfoCard_displaysWeatherInfoAndAdvice() {
        val weather = Weather(
            temperature = 12.5,
            windSpeed = 5.2,
            precipitation = 0.4,
            humidity = 78,
            weatherCode = 3,
            time = "2026-06-01T08:00"
        )

        val advice = "Погода прохладная. Лучше взять лёгкую куртку."
        val conditionText = "Пасмурно"

        composeTestRule.setContent {
            SkyMateTheme {
                WeatherInfoCard(
                    weather = weather,
                    advice = advice,
                    conditionText = conditionText
                )
            }
        }

        composeTestRule
            .onNodeWithText("Текущая погода")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Состояние")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Пасмурно")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Температура")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Скорость ветра")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Осадки")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Влажность")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Совет")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(advice)
            .assertIsDisplayed()
    }
}