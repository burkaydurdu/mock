package com.pow.services

import com.pow.models.Method
import com.pow.models.ResponseMediaType
import com.pow.sockets.RequestClientWebSocket
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.annotation.Client
import io.micronaut.websocket.RxWebSocketClient
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class ApiService {

    @Inject lateinit var userService: UserService

    @Inject
    @field:Client("http://localhost:8080")
    lateinit var webSocketClient: RxWebSocketClient

    private fun sendSocket(userId: UUID,
                           workspaceId: UUID,
                           requestId: UUID,
                           body: HashMap<String, Any>?,
                           httpRequest: HttpRequest<*>) {
        val chatClient: RequestClientWebSocket = webSocketClient
            .connect(RequestClientWebSocket::class.java, "/request/${userId}/${workspaceId}/${requestId}")
            .blockingFirst()
        val data: HashMap<String, Any?> = hashMapOf();
        data["body"] = body
        data["header"] = httpRequest.headers
        data["query"] = httpRequest.parameters
        data["address"] = httpRequest.remoteAddress.toString()
        data["uri"] = httpRequest.uri.toString()
        chatClient.send(data)
        chatClient.close()
    }

    fun findApi(
        userId: UUID,
        workspaceName: String,
        method: String,
        path: String,
        statusCode: Int,
        body: HashMap<String, Any>? = null,
        httpRequest: HttpRequest<*>): HttpResponse<*> {
        val user = userService.findById(userId)

        if (user.isPresent) {
            val workspace = user.get().workspaces?.find { w -> w.name == workspaceName }
            workspace?.let { _workspace ->
                val request = _workspace.requests?.find { r -> r.method == Method.valueOf(method) && r.path == path }
                request?.let { _request ->
                    val response = _request.responses?.find { r -> r.code == statusCode }
                    sendSocket(userId, _workspace.id, _request.id, body, httpRequest)
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