package ru.mirea.kt.ribo.skymate.util

import ru.mirea.kt.ribo.skymate.domain.model.District

object DistrictDataSource {

    val districts: List<District> = listOf(
        District(
            id = "cao",
            name = "Центральный административный округ",
            shortName = "ЦАО",
            description = "Центральная часть Москвы: Арбат, Тверской, Басманный, Пресненский и другие районы.",
            latitude = 55.75396,
            longitude = 37.62039
        ),
        District(
            id = "sao",
            name = "Северный административный округ",
            shortName = "САО",
            description = "Север Москвы: Аэропорт, Беговой, Войковский, Головинский, Коптево и другие районы.",
            latitude = 55.83970,
            longitude = 37.52570
        ),
        District(
            id = "svao",
            name = "Северо-Восточный административный округ",
            shortName = "СВАО",
            description = "Северо-восток Москвы: Алексеевский, Бибирево, Отрадное, Марьина Роща, Ростокино и другие районы.",
            latitude = 55.86320,
            longitude = 37.60490
        ),
        District(
            id = "vao",
            name = "Восточный административный округ",
            shortName = "ВАО",
            description = "Восточная часть Москвы: Измайлово, Новогиреево, Перово, Соколиная Гора и другие районы.",
            latitude = 55.78770,
            longitude = 37.77560
        ),
        District(
            id = "uvao",
            name = "Юго-Восточный административный округ",
            shortName = "ЮВАО",
            description = "Юго-восток Москвы: Люблино, Марьино, Кузьминки, Печатники, Текстильщики и другие районы.",
            latitude = 55.69290,
            longitude = 37.75430
        ),
        District(
            id = "uao",
            name = "Южный административный округ",
            shortName = "ЮАО",
            description = "Южная часть Москвы: Чертаново, Бирюлёво, Нагатино, Орехово-Борисово и другие районы.",
            latitude = 55.61160,
            longitude = 37.68130
        ),
        District(
            id = "uzao",
            name = "Юго-Западный административный округ",
            shortName = "ЮЗАО",
            description = "Юго-запад Москвы: Гагаринский, Академический, Ясенево, Коньково, Тёплый Стан и другие районы.",
            latitude = 55.66520,
            longitude = 37.51520
        ),
        District(
            id = "zao",
            name = "Западный административный округ",
            shortName = "ЗАО",
            description = "Запад Москвы: Кунцево, Фили-Давыдково, Раменки, Солнцево, Дорогомилово и другие районы.",
            latitude = 55.72800,
            longitude = 37.44350
        ),
        District(
            id = "szao",
            name = "Северо-Западный административный округ",
            shortName = "СЗАО",
            description = "Северо-запад Москвы: Строгино, Щукино, Хорошёво-Мнёвники, Митино, Тушино и другие районы.",
            latitude = 55.82960,
            longitude = 37.45190
        ),
        District(
            id = "zelao",
            name = "Зеленоградский административный округ",
            shortName = "ЗелАО",
            description = "Зеленоградский округ Москвы, расположенный отдельно от основной территории города.",
            latitude = 55.98250,
            longitude = 37.18140
        ),
        District(
            id = "tinao",
            name = "Троицкий и Новомосковский административные округа",
            shortName = "ТиНАО",
            description = "Новые территории Москвы: Троицк, Московский, Щербинка, Коммунарка и другие населённые пункты.",
            latitude = 55.48498,
            longitude = 37.30529
        )
    )
}