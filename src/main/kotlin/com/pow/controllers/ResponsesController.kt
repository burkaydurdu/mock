package com.pow.controllers

import com.pow.models.Response
import com.pow.models.User
import com.pow.requests.CreateResponseRequest
import com.pow.requests.UpdateResponseRequest
import com.pow.serializers.ErrorDTO
import com.pow.serializers.ResponseDTO
import com.pow.services.RequestService
import com.pow.services.ResponseService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional

@Controller("/responses")
open class ResponsesController {

    @Inject lateinit var responseService: ResponseService
    @Inject lateinit var requestService: RequestService

    @Get("/{id}")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun show(authentication: Authentication, @PathVariable id: UUID): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val response = responseService.findById(id)

        return if (response.isPresent && response.get().request.workspace.user.id == currentUser.id) {
            HttpResponse.ok(ResponseDTO(response.get().id,
                                        response.get().headers,
                                        response.get().body,
                                        response.get().mimeType,
                                        response.get().code))
        } else {
            HttpResponse.badRequest(ErrorDTO("Not found!!"))
        }
    }

    @Get("/request/{id}")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun index(authentication: Authentication, @PathVariable id: UUID): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val request = requestService.findById(id)

        return if (request.isPresent && request.get().workspace.user.id == currentUser.id) {
            request.get().responses?.let {
                val responsesDTO = responseService.getResponseList(it)
                HttpResponse.ok(responsesDTO)
            } ?: HttpResponse.notFound(ErrorDTO("No data!!"))
        } else {
            HttpResponse.notFound(ErrorDTO("No data!!"))
        }
    }

    @Post("/")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun create(authentication: Authentication, @Body request: CreateResponseRequest): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val req = requestService.findById(request.requestId)

        if (req.isPresent && req.get().workspace.user.id == currentUser.id) {
            Response(request.headers, request.body, request.mimeType, request.code, req.get()).also {
                val response = responseService.save(it)

                return if (response != null) {
                    HttpResponse.ok(ResponseDTO(response.id,
                                                response.headers,
                                                response.body,
                                                response.mimeType,
                                                response.code))
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
    open fun update(authentication: Authentication, @Body request: UpdateResponseRequest): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        val response = responseService.findById(request.id)
        if (response.isPresent) {
            if (response.get().request.workspace.user.id != currentUser.id) {
                return HttpResponse.badRequest(ErrorDTO("Access denied!!"))
            }

            val updatedResponse = responseService.update(response.get(),
                                                         request.headers,
                                                         request.body,
                                                         request.mimeType,
                                                         request.code)

            return if (updatedResponse != null) {
                HttpResponse.ok(ResponseDTO(updatedResponse.id,
                                            updatedResponse.headers,
                                            updatedResponse.body,
                                            updatedResponse.mimeType,
                                            updatedResponse.code))
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
        val response = responseService.findById(id)

        return if (response.isPresent && response.get().request.workspace.user.id == currentUser.id) {
            responseService.deleteIdBy(response.get())

            HttpResponse.ok(ResponseDTO(response.get().id,
                response.get().headers,
                response.get().body,
                response.get().mimeType,
                response.get().code))
        } else {
            HttpResponse.badRequest(ErrorDTO("Not found!!"))
        }
    }
}