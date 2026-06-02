package ru.mirea.kt.ribo.skymate.domain.usecase.location

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.model.UserLocation
import ru.mirea.kt.ribo.skymate.domain.repository.DistrictRepository

class DetectNearestDistrictUseCaseTest {

    private val fakeDistricts = listOf(
        District(
            id = "cao",
            name = "Центральный административный округ",
            shortName = "ЦАО",
            description = "Центральная часть Москвы.",
            latitude = 55.75396,
            longitude = 37.62039
        ),
        District(
            id = "zelao",
            name = "Зеленоградский административный округ",
            shortName = "ЗелАО",
            description = "Зеленоградский округ Москвы.",
            latitude = 55.98250,
            longitude = 37.18140
        ),
        District(
            id = "uao",
            name = "Южный административный округ",
            shortName = "ЮАО",
            description = "Южная часть Москвы.",
            latitude = 55.61160,
            longitude = 37.68130
        )
    )

    private val fakeRepository = object : DistrictRepository {

        override fun getDistricts(): List<District> {
            return fakeDistricts
        }

        override fun getDistrictById(id: String): District? {
            return fakeDistricts.firstOrNull { district ->
                district.id == id
            }
        }
    }

    private val useCase = DetectNearestDistrictUseCase(fakeRepository)

    @Test
    fun `invoke returns central district for Moscow center location`() {
        val location = UserLocation(
            latitude = 55.7558,
            longitude = 37.6173
        )

        val result = useCase(location)

        assertEquals("cao", result?.id)
    }

    @Test
    fun `invoke returns zelenograd district for zelenograd location`() {
        val location = UserLocation(
            latitude = 55.9820,
            longitude = 37.1800
        )

        val result = useCase(location)

        assertEquals("zelao", result?.id)
    }
}