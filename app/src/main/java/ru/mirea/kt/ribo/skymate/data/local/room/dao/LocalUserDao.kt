package ru.mirea.kt.ribo.skymate.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.mirea.kt.ribo.skymate.data.local.room.entity.LocalUserEntity

@Dao
interface LocalUserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(
        user: LocalUserEntity
    )

    @Query(
        """
        SELECT * FROM local_users
        WHERE username = :login OR email = :login
        LIMIT 1
        """
    )
    suspend fun getUserByUsernameOrEmail(
        login: String
    ): LocalUserEntity?

    @Query("SELECT COUNT(*) FROM local_users WHERE username = :username")
    suspend fun countByUsername(
        username: String
    ): Int

    @Query("SELECT COUNT(*) FROM local_users WHERE email = :email")
    suspend fun countByEmail(
        email: String
    ): Int
}