package ru.mirea.kt.ribo.skymate.domain.model

data class District(
    val id: String,
    val name: String,
    val shortName: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)