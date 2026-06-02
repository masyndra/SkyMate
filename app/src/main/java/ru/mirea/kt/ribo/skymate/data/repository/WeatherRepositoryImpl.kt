package ru.mirea.kt.ribo.skymate.data.repository

import retrofit2.HttpException
import ru.mirea.kt.ribo.skymate.data.remote.api.WeatherApi
import ru.mirea.kt.ribo.skymate.data.remote.dto.toWeather
import ru.mirea.kt.ribo.skymate.domain.model.District
import ru.mirea.kt.ribo.skymate.domain.model.Weather
import ru.mirea.kt.ribo.skymate.domain.repository.WeatherRepository
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherByDistrict(
        district: District
    ): Result<Weather> {
        return try {
            val response = weatherApi.getCurrentWeather(
                latitude = district.latitude,
                longitude = district.longitude
            )

            Result.success(response.toWeather())
        } catch (exception: HttpException) {
            Result.failure(
                Exception("Ошибка сервера погоды: ${exception.code()}")
            )
        } catch (exception: IOException) {
            Result.failure(
                Exception("Нет подключения к интернету")
            )
        } catch (exception: Exception) {
            Result.failure(
                Exception(exception.message ?: "Не удалось загрузить погоду")
            )
        }
    }
}