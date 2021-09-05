package com.pow.repositories

import com.pow.models.ForgotAuthToken
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface ForgotAuthTokenRepository: CrudRepository<ForgotAuthToken, UUID> {
    fun save(forgotAuthToken: ForgotAuthToken): ForgotAuthToken
}