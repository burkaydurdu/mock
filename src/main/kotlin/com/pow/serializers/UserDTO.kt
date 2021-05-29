package com.pow.serializers

import io.micronaut.core.annotation.Introspected
import java.util.*

@Introspected
data class UserDTO(var id: UUID, var name: String, var email: String, var token: String? = null)