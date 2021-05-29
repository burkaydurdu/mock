package com.pow.controllers

import com.pow.models.User
import com.pow.models.Workspace
import com.pow.requests.WorkspaceCreateRequest
import com.pow.requests.WorkspaceUpdateRequest
import com.pow.serializers.ErrorDTO
import com.pow.serializers.WorkspaceDTO
import com.pow.services.WorkspaceService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.security.authentication.Authentication
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional

@Controller("/workspaces")
open class WorkspacesController {

    @Inject
    lateinit var workspaceService: WorkspaceService

    @Get("/{id}")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun show(authentication: Authentication, @PathVariable id: UUID): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val workspace = workspaceService.findById(id)

        return if (workspace.isPresent && workspace.get().user.id == currentUser.id) {
            HttpResponse.ok(WorkspaceDTO(workspace.get().id, workspace.get().name))
        } else {
            HttpResponse.badRequest(ErrorDTO("Not found!!"))
        }
    }

    @Get("/")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun index(authentication: Authentication): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val workspaces = workspaceService.findByUser(currentUser)

        return if (workspaces != null) {
            HttpResponse.ok(workspaces)
        } else {
            HttpResponse.notFound(ErrorDTO("No data!!"))
        }
    }

    @Post("/")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun create(authentication: Authentication, @Body request: WorkspaceCreateRequest): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User

        Workspace(request.name, currentUser).also {
            val workspace = workspaceService.save(it)
            return if (workspace != null) {
                HttpResponse.ok(WorkspaceDTO(workspace.id, workspace.name))
            } else {
                HttpResponse.badRequest(ErrorDTO("Something went wrong!!"))
            }
        }
    }

    @Put("/")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun update(authentication: Authentication, @Body request: WorkspaceUpdateRequest): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val workspace: Workspace = workspaceService.findByIdAndUser(request.id, currentUser)
            ?: return HttpResponse.notFound(ErrorDTO("Workspace not found!!"))

        val updatedWorkspace = workspaceService.update(workspace, request.name)

        return if (updatedWorkspace != null) {
            HttpResponse.ok(WorkspaceDTO(updatedWorkspace.id, updatedWorkspace.name))
        } else {
            HttpResponse.badRequest(ErrorDTO("Something went wrong!!"))
        }
    }
}