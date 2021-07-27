package com.pow.repositories

import com.pow.models.Method
import com.pow.models.Request
import com.pow.models.Workspace
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface RequestRepository: CrudRepository<Request, UUID> {
    fun findByPathAndMethodAndWorkspace(path: String, method: Method, workspace: Workspace): Request?
    fun save(request: Request): Request
    fun update(request: Request): Request
    fun deleteIdBy(request: Request)
}