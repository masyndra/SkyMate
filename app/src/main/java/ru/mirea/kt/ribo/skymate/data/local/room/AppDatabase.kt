package ru.mirea.kt.ribo.skymate.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mirea.kt.ribo.skymate.data.local.room.dao.LocalUserDao
import ru.mirea.kt.ribo.skymate.data.local.room.entity.LocalUserEntity

@Database(
    entities = [
        LocalUserEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun localUserDao(): LocalUserDao
}