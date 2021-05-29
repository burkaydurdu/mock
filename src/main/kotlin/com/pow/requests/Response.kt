package com.pow.requests

import com.pow.models.MimeType
import java.util.*

data class CreateResponseRequest(val headers: Map<String, Any>,
                                 val body: String,
                                 val mimeType: MimeType,
                                 val code: Int,
                                 val requestId: UUID)

data class UpdateResponseRequest(val id: UUID,
                                 val headers: Map<String, Any>,
                                 val body: String,
                                 val mimeType: MimeType)