package com.pow.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vladmihalcea.hibernate.type.array.ListArrayType
import org.hibernate.annotations.TypeDef
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
@TypeDef(name = "string-list", typeClass = ListArrayType::class)
data class User(
    @NotNull
    var name: String,

    @Email
    @Column(unique = true)
    @NotNull
    val email: String,

    @NotNull
    val password: String,

    @Column(unique = true)
    var token: String? = null,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    var workspaces: List<Workspace>? = null
): MockEntity()
