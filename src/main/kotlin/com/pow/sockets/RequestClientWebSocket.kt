package com.pow.sockets

import io.micronaut.http.HttpRequest
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.ClientWebSocket
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen

@ClientWebSocket("/request/{userId}/{workspaceId}/{requestId}")
abstract class RequestClientWebSocket : AutoCloseable {

    lateinit var session: WebSocketSession

    @OnOpen
    fun onOpen(session: WebSocketSession, request: HttpRequest<*>) {
        this.session = session
    }

    @OnMessage
    fun onMessage(message: String) {
    }

    @OnClose
    fun onClose() {

    }

    abstract fun send(message: HashMap<String, Any?>)
}