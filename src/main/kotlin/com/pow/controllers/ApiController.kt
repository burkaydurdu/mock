package com.pow.controllers

import com.pow.services.ApiService
import io.micronaut.http.*
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional

@Controller("/api")
open class ApiController {

    @Inject lateinit var apiService: ApiService

    @Get(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun get(request: HttpRequest<*>,
                 headers: HttpHeaders,
                 @PathVariable userId: UUID,
                 @PathVariable workspaceName: String,
                 @PathVariable path: String?,
                 @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(userId, workspaceName, "GET", path ?: "", statusCode ?: 200)
    }

    @Post(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun post(@PathVariable userId: UUID,
                  @PathVariable workspaceName: String,
                  @PathVariable path: String?,
                  @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(userId, workspaceName, "POST", path ?: "", statusCode ?: 200)
    }

    @Put(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun put(@PathVariable userId: UUID,
                 @PathVariable workspaceName: String,
                 @PathVariable path: String?,
                 @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(userId, workspaceName, "PUT", path ?: "", statusCode ?: 200)
    }

    @Delete(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun delete(@PathVariable userId: UUID,
                    @PathVariable workspaceName: String,
                    @PathVariable path: String?,
                    @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(userId, workspaceName, "DELETE", path ?: "", statusCode ?: 200)
    }

    @Options(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun options(@PathVariable userId: UUID,
                     @PathVariable workspaceName: String,
                     @PathVariable path: String?,
                     @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(userId, workspaceName, "OPTIONS", path ?: "", statusCode ?: 200)
    }

    @Patch(uris= ["/{userId}/{workspaceName}/{path:.*}", "/{userId}/{workspaceName}"])
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun patch(@PathVariable userId: UUID,
                   @PathVariable workspaceName: String,
                   @PathVariable path: String?,
                   @QueryValue statusCode: Int?): HttpResponse<*> {
        return apiService.findApi(userId, workspaceName, "PATCH", path ?: "", statusCode ?: 200)
    }
}