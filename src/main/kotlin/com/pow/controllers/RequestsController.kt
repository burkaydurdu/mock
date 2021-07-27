package com.pow.controllers

import com.pow.models.Request
import com.pow.models.User
import com.pow.models.Workspace
import com.pow.requests.CreateRequest
import com.pow.requests.UpdateRequest
import com.pow.serializers.ErrorDTO
import com.pow.serializers.RequestDTO
import com.pow.services.RequestService
import com.pow.services.WorkspaceService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional

@Controller("/requests")
open class RequestsController {

    @Inject lateinit var requestService: RequestService
    @Inject lateinit var workspaceService: WorkspaceService

    @Get("/{id}")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun show(authentication: Authentication, @PathVariable id: UUID): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val req = requestService.findById(id)

        return if (req.isPresent) {
            val workspace = workspaceService.findById(req.get().workspace.id)

            if (workspace.get().user.id == currentUser.id) {
                HttpResponse.ok(RequestDTO(req.get().id, req.get().path, req.get().method))
            } else {
                HttpResponse.badRequest(ErrorDTO("Not found!!"))
            }
        } else {
            HttpResponse.badRequest(ErrorDTO("Not found!!"))
        }
    }

    @Get("/workspace/{id}")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun index(authentication: Authentication, @PathVariable id: UUID): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val workspaces = workspaceService.findByIdAndUser(id, currentUser) ?:
            return HttpResponse.notFound(ErrorDTO("Not found!!"))

        return workspaces.requests?.let {
            val requestsDTO = requestService.getRequestList(it)
            HttpResponse.ok(requestsDTO)
        } ?: HttpResponse.notFound(ErrorDTO("No data!!"))
    }

    @Post("/")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun create(authentication: Authentication, @Body request: CreateRequest): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val workspace = workspaceService.findById(request.workspaceId)

        if (workspace.isPresent && workspace.get().user.id == currentUser.id) {
            Request(request.path, request.method, workspace.get()).also {
                val req = requestService.save(it)

                return if (req != null) {
                    HttpResponse.ok(RequestDTO(req.id, req.path, req.method))
                } else {
                    HttpResponse.badRequest(ErrorDTO("Something went wrong!!"))
                }
            }
        } else {
            return HttpResponse.notFound(ErrorDTO("No data!!"))
        }
    }

    @Put("/")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun update(authentication: Authentication, @Body request: UpdateRequest): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val req = requestService.findById(request.id)
        if (req.isPresent) {
            val workspace: Workspace = workspaceService.findByIdAndUser(req.get().workspace.id, currentUser)
                ?: return HttpResponse.notFound(ErrorDTO("Workspace not found!!"))

            if (workspace.id != req.get().workspace.id) {
                return HttpResponse.badRequest(ErrorDTO("Something went wrong!!"))
            }

            val updatedRequest = requestService.update(req.get(), request.path, request.method)
            return if (updatedRequest != null) {
                HttpResponse.ok(RequestDTO(updatedRequest.id, updatedRequest.path, updatedRequest.method))
            } else {
                HttpResponse.badRequest(ErrorDTO("Something went wrong!!"))
            }
        } else {
            return HttpResponse.notFound(ErrorDTO("No data!!"))
        }
    }

    @Delete("/{id}")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun delete(authentication: Authentication, @PathVariable id: UUID): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val req = requestService.findById(id)

        return if (req.isPresent) {
            val workspace = workspaceService.findById(req.get().workspace.id)

            if (workspace.get().user.id == currentUser.id) {
                requestService.deleteIdBy(req.get())

                HttpResponse.ok(RequestDTO(req.get().id, req.get().path, req.get().method))
            } else {
                HttpResponse.badRequest(ErrorDTO("Not found!!"))
            }
        } else {
            HttpResponse.badRequest(ErrorDTO("Not found!!"))
        }
    }
}