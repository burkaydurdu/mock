package com.pow.config

import io.micronaut.context.annotation.ConfigurationProperties
import javax.validation.constraints.NotBlank

@ConfigurationProperties("smtp.auth")
class SmtpConfig {

    @NotBlank
    var username: String = ""

    @NotBlank
    var password: String = ""

    @NotBlank
    var domain: String = ""

}
