package com.pow.sockets

import io.micronaut.http.HttpRequest
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.*

@ClientWebSocket("/request/{userId}/{workspaceId}/{requestId}")
abstract class RequestClientWebSocket : AutoCloseable {

    lateinit var session: WebSocketSession

    @OnOpen
    fun onOpen(session: WebSocketSession, request: HttpRequest<*>) {
        this.session = session
    }

    @OnMessage
    fun onMessage(message: String) {}

    @OnClose
    fun onClose(session: WebSocketSession) {}

    abstract fun send(message: HashMap<String, Any?>)
}