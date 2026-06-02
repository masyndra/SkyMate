package ru.mirea.kt.ribo.skymate.data.local.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "local_users",
    indices = [
        Index(value = ["username"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class LocalUserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val username: String,

    val email: String,

    val fullName: String,

    val passwordHash: String,

    val createdAtMillis: Long
)