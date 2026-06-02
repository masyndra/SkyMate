package ru.mirea.kt.ribo.skymate.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mirea.kt.ribo.skymate.data.local.room.AppDatabase
import ru.mirea.kt.ribo.skymate.data.local.room.dao.LocalUserDao
import ru.mirea.kt.ribo.skymate.util.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLocalUserDao(
        appDatabase: AppDatabase
    ): LocalUserDao {
        return appDatabase.localUserDao()
    }
}