package com.pow.requests

import com.pow.models.Method
import java.util.*

data class CreateRequest(val path: String, val method: Method, val workspaceId: UUID)
data class UpdateRequest(val id: UUID, val path: String, val method: Method)