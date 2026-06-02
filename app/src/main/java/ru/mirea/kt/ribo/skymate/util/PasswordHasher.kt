package ru.mirea.kt.ribo.skymate.util

import java.security.MessageDigest

object PasswordHasher {

    fun hash(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())

        return bytes.joinToString(separator = "") { byte ->
            "%02x".format(byte)
        }
    }
}