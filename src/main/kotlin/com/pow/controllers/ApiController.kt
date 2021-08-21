package com.pow.controllers

import com.pow.services.ApiService
import io.micronaut.http.*
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional
import kotlin.collections.HashMap

@Controller("/api")
open class ApiController {

    @Inject lateinit var apiService: ApiService

    @Get(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun get(request: HttpRequest<*>,
                 @PathVariable userId: UUID,
                 @PathVariable workspaceName: String,
                 @PathVariable path: String?,
                 @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(
            userId,
            workspaceName,
            "GET",
            path ?: "",
            statusCode ?: 200,
            null,
            request,
        )
    }

    @Post(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun post(request: HttpRequest<*>,
                  @Body body: HashMap<String, Any>,
                  @PathVariable userId: UUID,
                  @PathVariable workspaceName: String,
                  @PathVariable path: String?,
                  @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(
            userId,
            workspaceName,
            "POST",
            path ?: "",
            statusCode ?: 200,
            body,
            request
        )
    }

    @Put(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun put(request: HttpRequest<*>,
                 @Body body: HashMap<String, Any>,
                 @PathVariable userId: UUID,
                 @PathVariable workspaceName: String,
                 @PathVariable path: String?,
                 @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(
            userId,
            workspaceName,
            "PUT",
            path ?: "",
            statusCode ?: 200,
            body,
            request
        )
    }

    @Delete(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun delete(request: HttpRequest<*>,
                    @Body body: HashMap<String, Any>,
                    @PathVariable userId: UUID,
                    @PathVariable workspaceName: String,
                    @PathVariable path: String?,
                    @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(
            userId,
            workspaceName,
            "DELETE",
            path ?: "",
            statusCode ?: 200,
            body,
            request
        )
    }

    @Options(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun options(request: HttpRequest<*>,
                     @Body body: HashMap<String, Any>,
                     @PathVariable userId: UUID,
                     @PathVariable workspaceName: String,
                     @PathVariable path: String?,
                     @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(
            userId,
            workspaceName,
            "OPTIONS",
            path ?: "",
            statusCode ?: 200,
            body,
            request
        )
    }

    @Patch(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun patch(request: HttpRequest<*>,
                   @Body body: HashMap<String, Any>,
                   @PathVariable userId: UUID,
                   @PathVariable workspaceName: String,
                   @PathVariable path: String?,
                   @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(
            userId,
            workspaceName,
            "PATCH",
            path ?: "",
            statusCode ?: 200,
            body,
            request
        )
    }
}