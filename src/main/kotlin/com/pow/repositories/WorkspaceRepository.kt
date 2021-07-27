package com.pow.repositories

import com.pow.models.User
import com.pow.models.Workspace
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface WorkspaceRepository: CrudRepository<Workspace, UUID> {
    fun findByUser(user: User): List<Workspace>?
    fun findByIdAndUser(id: UUID, user: User): Workspace?
    fun findByNameAndUser(name: String, user: User): Workspace?
    fun save(workspace: Workspace): Workspace
    fun update(workspace: Workspace): Workspace
    fun deleteIdBy(workspace: Workspace)
}