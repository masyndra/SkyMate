package ru.mirea.kt.ribo.skymate.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {

    private val dateFormat = SimpleDateFormat(
        "dd.MM.yyyy HH:mm",
        Locale.getDefault()
    )

    fun formatDateTime(
        millis: Long
    ): String {
        return dateFormat.format(Date(millis))
    }
}