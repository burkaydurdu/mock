package com.pow.requests

import java.util.*

data class WorkspaceCreateRequest(val name: String)
data class WorkspaceUpdateRequest(val id: UUID, val name: String)