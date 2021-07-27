package com.pow.services

import com.pow.models.User
import com.pow.models.Workspace
import com.pow.repositories.WorkspaceRepository
import com.pow.serializers.WorkspaceDTO
import java.util.*
import javax.inject.Inject

class WorkspaceService {
    @Inject lateinit var workspaceRepository: WorkspaceRepository

    fun findById(id: UUID): Optional<Workspace> = workspaceRepository.findById(id)

    fun deleteIdBy(workspace: Workspace) = workspaceRepository.deleteIdBy(workspace)

    fun findByUser(user: User): List<WorkspaceDTO>? {
        val workspaces: List<Workspace>? = workspaceRepository.findByUser(user)
        val workspaceDTO: MutableList<WorkspaceDTO> = mutableListOf()
        workspaces?.map {
            workspaceDTO.add(WorkspaceDTO(it.id, it.name))
        }

        return workspaceDTO
    }

    fun findByIdAndUser(id: UUID, user: User) = workspaceRepository.findByIdAndUser(id, user)

    fun save(workspace: Workspace): Workspace? {
        workspaceRepository.findByNameAndUser(workspace.name, workspace.user) ?: return workspaceRepository.save(workspace)
        return null
    }

    fun update(workspace: Workspace, name: String): Workspace? {
        return if (workspaceRepository.findByNameAndUser(name, workspace.user) == null) {
            workspace.name = name
            workspace
        } else {
            null
        }
    }
}