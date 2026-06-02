package ru.mirea.kt.ribo.skymate.presentation.districtlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.presentation.theme.SkyMateTheme

class DistrictListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun districtListContent_displaysDistricts() {
        val districts = listOf(
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

        composeTestRule.setContent {
            SkyMateTheme {
                DistrictListContent(
                    uiState = DistrictListUiState(
                        districts = districts,
                        selectedDistrictId = "cao"
                    ),
                    onBackClick = {},
                    onDistrictClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Выбор района")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("ЦАО")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("САО")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Центральный административный округ")
            .assertIsDisplayed()
    }

    @Test
    fun districtListContent_invokesClickWhenDistrictSelected() {
        val districts = listOf(
            District(
                id = "cao",
                name = "Центральный административный округ",
                shortName = "ЦАО",
                description = "Центральная часть Москвы.",
                latitude = 55.75396,
                longitude = 37.62039
            )
        )

        var selectedDistrictId = ""

        composeTestRule.setContent {
            SkyMateTheme {
                DistrictListContent(
                    uiState = DistrictListUiState(
                        districts = districts,
                        selectedDistrictId = null
                    ),
                    onBackClick = {},
                    onDistrictClick = { districtId ->
                        selectedDistrictId = districtId
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithText("ЦАО")
            .performClick()

        assertEquals("cao", selectedDistrictId)
    }
}