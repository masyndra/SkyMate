package ru.mirea.kt.ribo.skymate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mirea.kt.ribo.skymate.data.repository.AuthRepositoryImpl
import ru.mirea.kt.ribo.skymate.data.repository.DistrictRepositoryImpl
import ru.mirea.kt.ribo.skymate.data.repository.LocationRepositoryImpl
import ru.mirea.kt.ribo.skymate.data.repository.UserPreferencesRepositoryImpl
import ru.mirea.kt.ribo.skymate.data.repository.WeatherRepositoryImpl
import ru.mirea.kt.ribo.skymate.domain.repository.AuthRepository
import ru.mirea.kt.ribo.skymate.domain.repository.DistrictRepository
import ru.mirea.kt.ribo.skymate.domain.repository.LocationRepository
import ru.mirea.kt.ribo.skymate.domain.repository.UserPreferencesRepository
import ru.mirea.kt.ribo.skymate.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindDistrictRepository(
        districtRepositoryImpl: DistrictRepositoryImpl
    ): DistrictRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}