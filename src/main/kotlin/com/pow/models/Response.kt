package com.pow.models

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import io.micronaut.http.MediaType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "responses", uniqueConstraints = [UniqueConstraint(columnNames = ["code", "request_id"])])
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class Response(
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var headers: Map<String, Any>? = null,

    var body: String? = null,

    @NotNull
    @Enumerated(EnumType.STRING)
    var mimeType: MimeType,

    @NotNull
    var code: Int,

    @NotNull
    @ManyToOne
    val request: Request
): MockEntity()

enum class MimeType {
    JSON, XML
}

enum class ResponseMediaType(val type: String) {
    JSON(MediaType.APPLICATION_JSON),
    XML(MediaType.APPLICATION_XML)
}

