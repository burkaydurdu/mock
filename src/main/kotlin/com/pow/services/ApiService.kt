package com.pow.services

import com.pow.models.Method
import com.pow.models.ResponseMediaType
import io.micronaut.http.HttpResponse
import java.util.*
import javax.inject.Inject

class ApiService {

    @Inject lateinit var userService: UserService

    fun findApi(userId: UUID, workspaceName: String, method: String, path: String, statusCode: Int): HttpResponse<*> {
        val user = userService.findById(userId)

        if (user.isPresent) {
            val workspace = user.get().workspaces?.find { w -> w.name == workspaceName }
            workspace?.let { _workspace ->
                val request = _workspace.requests?.find { r -> r.method == Method.valueOf(method) && r.path == path }
                request?.let { _request ->
                    val response = _request.responses?.find { r -> r.code == statusCode }
                    response?.let { _response ->
                        return HttpResponse
                            .ok("")
                            .headers {
                                _response.headers?.map { (key, value) ->
                                    it[key] = value as String
                                }
                            }
                            .status(_response.code)
                            .contentType(ResponseMediaType.valueOf(_response.mimeType.name).type)
                            .body(_response.body)
                    }
                }
            }
        }
        return HttpResponse.notFound("Not found!!")
    }
}