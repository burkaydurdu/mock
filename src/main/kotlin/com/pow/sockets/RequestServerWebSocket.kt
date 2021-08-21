package com.pow.sockets

import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.websocket.WebSocketBroadcaster
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.*

import java.util.function.Predicate

@ServerWebSocket("/request/{userId}/{workspaceId}/{requestId}")
@Secured(SecurityRule.IS_ANONYMOUS)
open class RequestServerWebSocket(private val broadcaster: WebSocketBroadcaster) {

    @OnMessage
    fun onMessage(userId: String, workspaceId: String, requestId: String, message: HashMap<String, Any>, session: WebSocketSession) {
        val channel = "/request/${userId}/${workspaceId}/${requestId}"
        broadcaster.broadcastSync(message, isValid(channel, session))
    }

    private fun isValid(channel: String, session: WebSocketSession): Predicate<WebSocketSession> {
        return Predicate<WebSocketSession> {
            (it !== session && it.requestURI.toString() == channel)
        }
    }
}