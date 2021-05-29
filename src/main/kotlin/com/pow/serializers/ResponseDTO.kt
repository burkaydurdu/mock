package com.pow.serializers

import com.pow.models.MimeType
import java.util.*

data class ResponseDTO(val id: UUID,
                       val headers: Map<String, Any>?,
                       val body: String?,
                       val mimeType: MimeType,
                       val code: Int)