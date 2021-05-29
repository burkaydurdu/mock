package com.pow.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vladmihalcea.hibernate.type.array.ListArrayType
import org.hibernate.annotations.TypeDef
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "requests", uniqueConstraints = [UniqueConstraint(columnNames = ["path", "method", "workspace_id"])])
@TypeDef(name = "string-list", typeClass = ListArrayType::class)
data class Request(
    @NotNull
    var path: String,

    @NotNull
    @Enumerated(EnumType.STRING)
    var method: Method,

    @ManyToOne
    @NotNull
    val workspace: Workspace,

    @OneToMany(mappedBy = "request", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    var responses: List<Response>? = null
): MockEntity()

enum class Method {
    GET, POST, PUT, DELETE, PATCH, OPTIONS
}
