package com.pow.repositories

import com.pow.models.Request
import com.pow.models.Response
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface ResponseRepository: CrudRepository<Response, UUID> {
    fun findByCodeAndRequest(code: Int, request: Request): Response?
    fun save(response: Response): Response
    fun update(response: Response): Response
    fun deleteIdBy(response: Response)
}