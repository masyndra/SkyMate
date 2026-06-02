package ru.mirea.kt.ribo.skymate.domain.usecase.weather

import org.junit.Assert.assertTrue
import org.junit.Test
import ru.mirea.kt.ribo.skymate.domain.model.Weather

class GenerateWeatherAdviceUseCaseTest {

    private val useCase = GenerateWeatherAdviceUseCase()

    @Test
    fun `invoke returns cold weather advice when temperature is below zero`() {
        val weather = Weather(
            temperature = -8.0,
            windSpeed = 3.0,
            precipitation = 0.0,
            humidity = 60,
            weatherCode = 0,
            time = "2026-05-31T12:00"
        )

        val advice = useCase(weather)

        assertTrue(
            advice.contains("Температура ниже нуля")
        )
    }

    @Test
    fun `invoke returns umbrella advice when precipitation is greater than zero`() {
        val weather = Weather(
            temperature = 12.0,
            windSpeed = 4.0,
            precipitation = 1.5,
            humidity = 70,
            weatherCode = 61,
            time = "2026-05-31T12:00"
        )

        val advice = useCase(weather)

        assertTrue(
            advice.contains("зонт") || advice.contains("дождевик")
        )
    }

    @Test
    fun `invoke returns wind advice when wind speed is high`() {
        val weather = Weather(
            temperature = 18.0,
            windSpeed = 13.0,
            precipitation = 0.0,
            humidity = 50,
            weatherCode = 0,
            time = "2026-05-31T12:00"
        )

        val advice = useCase(weather)

        assertTrue(
            advice.contains("Ветер достаточно сильный")
        )
    }

    @Test
    fun `invoke returns heat advice when temperature is high`() {
        val weather = Weather(
            temperature = 30.0,
            windSpeed = 2.0,
            precipitation = 0.0,
            humidity = 45,
            weatherCode = 0,
            time = "2026-05-31T12:00"
        )

        val advice = useCase(weather)

        assertTrue(
            advice.contains("жарко")
        )
    }
}