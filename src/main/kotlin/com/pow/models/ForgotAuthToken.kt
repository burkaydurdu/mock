package com.pow.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "forgot_auth_tokens")
data class ForgotAuthToken(

    @NotNull
    @Column(unique= true)
    var token: String,

    @OneToOne
    val user: User
): MockEntity()
