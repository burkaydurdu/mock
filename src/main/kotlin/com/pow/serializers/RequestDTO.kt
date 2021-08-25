package com.pow.serializers

import com.pow.models.Method
import java.util.*

data class RequestDTO(var id: UUID,
                      var path: String,
                      var method: Method,
                      var workspaceId: UUID)