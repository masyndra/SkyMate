package ru.mirea.kt.ribo.skymate.domain.usecase.district

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.repository.DistrictRepository

class GetDistrictByIdUseCaseTest {

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
            id = "sao",
            name = "Северный административный округ",
            shortName = "САО",
            description = "Север Москвы.",
            latitude = 55.83970,
            longitude = 37.52570
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

    private val useCase = GetDistrictByIdUseCase(fakeRepository)

    @Test
    fun `invoke returns district when id exists`() {
        val result = useCase("cao")

        assertEquals("ЦАО", result?.shortName)
        assertEquals("Центральный административный округ", result?.name)
    }

    @Test
    fun `invoke returns null when id does not exist`() {
        val result = useCase("unknown")

        assertNull(result)
    }
}