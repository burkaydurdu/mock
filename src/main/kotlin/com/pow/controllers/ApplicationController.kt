package com.pow.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Controller("/")
open class ApplicationController {

    @Get("/")
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun index(): HttpResponse<String> {
        return HttpResponse.ok("Puppet API")
    }
}