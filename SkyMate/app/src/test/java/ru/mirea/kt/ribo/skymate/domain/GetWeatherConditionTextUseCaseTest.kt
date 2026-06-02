package ru.mirea.kt.ribo.skymate.domain.usecase.weather

import org.junit.Assert.assertEquals
import org.junit.Test

class GetWeatherConditionTextUseCaseTest {

    private val useCase = GetWeatherConditionTextUseCase()

    @Test
    fun `invoke returns clear condition for code zero`() {
        val result = useCase(0)

        assertEquals("Ясно", result)
    }

    @Test
    fun `invoke returns rain condition for code sixty three`() {
        val result = useCase(63)

        assertEquals("Умеренный дождь", result)
    }

    @Test
    fun `invoke returns snow condition for code seventy five`() {
        val result = useCase(75)

        assertEquals("Сильный снег", result)
    }

    @Test
    fun `invoke returns no data when code is null`() {
        val result = useCase(null)

        assertEquals("Нет данных", result)
    }

    @Test
    fun `invoke returns unknown text for unsupported code`() {
        val result = useCase(999)

        assertEquals("Неизвестное состояние погоды", result)
    }
}