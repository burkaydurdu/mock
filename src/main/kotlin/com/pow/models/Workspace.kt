package com.pow.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vladmihalcea.hibernate.type.array.ListArrayType
import org.hibernate.annotations.TypeDef
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "workspaces", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "user_id"])])
@TypeDef(name = "string-list", typeClass = ListArrayType::class)
data class Workspace(
    @NotNull
    var name: String,

    @NotNull
    @ManyToOne
    val user: User,

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    var requests: List<Request>? = null
): MockEntity()