package ru.mirea.kt.ribo.skymate.domain.usecase.weather

import ru.mirea.kt.ribo.skymate.domain.model.Weather
import javax.inject.Inject

class GenerateWeatherAdviceUseCase @Inject constructor() {

    operator fun invoke(
        weather: Weather
    ): String {
        val advice = mutableListOf<String>()

        when {
            weather.temperature <= -15.0 -> {
                advice += "На улице сильный мороз. Лучше одеться максимально тепло: зимняя куртка, шапка, шарф и перчатки обязательны."
            }

            weather.temperature <= -5.0 -> {
                advice += "Температура ниже нуля. Рекомендуется тёплая верхняя одежда и обувь с нескользящей подошвой."
            }

            weather.temperature <= 5.0 -> {
                advice += "Довольно холодно. Лучше надеть куртку и не забыть про тёплый слой одежды."
            }

            weather.temperature <= 15.0 -> {
                advice += "Погода прохладная. Подойдёт лёгкая куртка или плотная кофта."
            }

            weather.temperature <= 25.0 -> {
                advice += "Температура комфортная. Можно одеться легко, но лучше учитывать ветер и осадки."
            }

            else -> {
                advice += "На улице жарко. Лучше выбрать лёгкую одежду и взять воду."
            }
        }

        if (weather.precipitation > 0.0) {
            advice += "Есть осадки, поэтому стоит взять зонт или дождевик."
        }

        if (weather.windSpeed >= 12.0) {
            advice += "Ветер достаточно сильный. Лучше избегать лёгких зонтов и учитывать это при прогулке."
        } else if (weather.windSpeed >= 7.0) {
            advice += "Ветер умеренный, на открытых участках может быть прохладнее."
        }

        weather.humidity?.let { humidity ->
            if (humidity >= 85) {
                advice += "Влажность высокая, из-за этого холод или жара могут ощущаться сильнее."
            }
        }

        return advice.joinToString(separator = " ")
    }
}