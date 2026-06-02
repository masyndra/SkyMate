package ru.mirea.kt.ribo.skymate.domain.model

data class User(
    val id: Int,
    val username: String,
    val fullName: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
    val imageUrl: String
)