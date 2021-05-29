package com.pow.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

object Utils {
    fun getGeneratedPasswordHash(password: String) : String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(password.toByteArray())).toString(16).padStart(32, '0')
    }

    fun generateKey(size: Int = 64): String {
        val secureRandom = SecureRandom()
        val encoder = Base64.getUrlEncoder().withoutPadding()
        val randomBytes = ByteArray(size)
        secureRandom.nextBytes(randomBytes)
        return encoder.encodeToString(randomBytes)
    }
}