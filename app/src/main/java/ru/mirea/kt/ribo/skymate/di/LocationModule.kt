package ru.mirea.kt.ribo.skymate.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mirea.kt.ribo.skymate.data.location.LocationTracker
import ru.mirea.kt.ribo.skymate.data.location.LocationTrackerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationProviderModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationBindModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(
        locationTrackerImpl: LocationTrackerImpl
    ): LocationTracker
}