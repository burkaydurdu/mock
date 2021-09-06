package com.pow.controllers

import com.pow.models.User
import com.pow.requests.*
import com.pow.serializers.ErrorDTO
import com.pow.serializers.UserDTO
import com.pow.services.UserService
import com.pow.utils.Utils
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import javax.inject.Inject
import javax.transaction.Transactional

@Controller("/users")
open class UsersController {

    @Inject lateinit var userService: UserService

    @Post("sign-up")
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun signUp(@Body request: SignUpRequest): HttpResponse<Any> {
        val password = Utils.getGeneratedPasswordHash(request.password)
        val token = Utils.generateKey(54)

        User(request.name, request.email, password, token).also {
            val user = userService.save(it)
            return if (user != null) {
                HttpResponse.ok(UserDTO(user.id, user.name, user.email, user.token))
            } else {
                HttpResponse.badRequest(ErrorDTO("Email is already exists"))
            }
        }
    }

    @Post("sign-in")
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun signIn(@Body request: SingInRequest): HttpResponse<*> {
        val password = Utils.getGeneratedPasswordHash(request.password)
        val user = userService.findByEmailAndPassword(request.email, password)
        return if (user != null) {
            user.token = Utils.generateKey(54)
            userService.update(user)
            HttpResponse.ok(UserDTO(user.id, user.name, user.email, user.token))
        } else {
            HttpResponse.notFound(ErrorDTO("User not found"))
        }
    }

    @Put("/")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun update(authentication: Authentication, @Body request: UserUpdateRequest): HttpResponse<*> {
        var currentUser: User = authentication.attributes["currentUser"] as User
        currentUser.name = request.name
        currentUser = userService.update(currentUser)
        return HttpResponse.ok(UserDTO(currentUser.id, currentUser.name, currentUser.email))
    }

    @Post("/logout")
    @Transactional
    @Secured(SecurityRule.IS_AUTHENTICATED)
    open fun logout(authentication: Authentication): HttpResponse<*> {
        val currentUser: User = authentication.attributes["currentUser"] as User
        currentUser.token = null
        userService.update(currentUser)
        return HttpResponse.ok(UserDTO(currentUser.id, currentUser.name, currentUser.email))
    }

    @Post("/send_reset_password")
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun sendResetPassword(@Body request: SendResetPasswordRequest): HttpResponse<*> {
        val currentUser: User = userService.findByEmail(request.email)
            ?: return HttpResponse.notFound(ErrorDTO("User not found"))

        userService.sendResetPasswordEmail(currentUser)

        return HttpResponse.ok(emptyMap<String, String>())
    }

    @Post("/reset_password")
    @Transactional
    @Secured(SecurityRule.IS_ANONYMOUS)
    open fun resetPassword(@Body request: ResetPasswordRequest): HttpResponse<*> {
        userService.updatePasswordWithForgotAuthToken(request.password, request.hash)
            ?: return HttpResponse.notFound(ErrorDTO("User not found"))

        return HttpResponse.ok(emptyMap<String, String>())
    }
}