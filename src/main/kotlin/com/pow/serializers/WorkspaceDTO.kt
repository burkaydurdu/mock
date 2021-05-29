package com.pow.serializers

import io.micronaut.core.annotation.Introspected
import java.util.*

@Introspected
data class WorkspaceDTO(var id: UUID, var name: String)