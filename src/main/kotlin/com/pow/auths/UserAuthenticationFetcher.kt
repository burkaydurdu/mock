package com.pow.auths

import com.pow.services.UserService
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.filters.AuthenticationFetcher
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAuthenticationFetcher: AuthenticationFetcher {

    @Inject lateinit var userService: UserService

    override fun fetchAuthentication(request: HttpRequest<*>?): Publisher<Authentication> {
        val parsedToken = readToken(request!!)

        val authentication: Optional<Authentication> = parsedToken
            .flatMap { token -> userService.findByToken(token) }
            .map { user ->
                object: Authentication {
                    override fun getName(): String {
                        return user.name
                    }

                    override fun getAttributes(): MutableMap<String, Any> {
                        return mutableMapOf(
                            "currentUser" to user
                        )
                    }
                }
            }
        return authentication.map { Flowable.just(it) }.orElse(Flowable.empty())
    }

    private fun readToken(request: HttpRequest<*>): Optional<String> {
        val headers = request.headers

        return headers
            .getFirst("Authorization")
            .map { it.trim() }
    }
}