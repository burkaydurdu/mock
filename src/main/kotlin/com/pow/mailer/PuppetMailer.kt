package com.pow.mailer

import com.pow.config.SmtpConfig

import org.simplejavamail.api.email.Email
import org.simplejavamail.api.mailer.Mailer
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder
import javax.inject.Singleton

@Singleton
class PuppetMailer(config: SmtpConfig) : PuppetMail {
    private lateinit var builder: Mailer

    private var template: String? = null
    private var username: String = config.username
    private var password: String = config.password
    private var domain: String = config.domain

    init {
        setBuilder()
    }

    override fun setBuilder() {
        builder = MailerBuilder.withSMTPServerHost("smtp.gmail.com")
                               .withSMTPServerPort(587)
                               .withSMTPServerUsername(username)
                               .withSMTPServerPassword(password)
                               .buildMailer()
    }

    override fun sendMail(email: String, name: String, subject: String) {
        if (template.isNullOrBlank()) return

        val simpleEmail: Email = EmailBuilder.startingBlank()
                                             .from("PuppetAPI","puppetapi@gmail.com")
                                             .to(name, email)
                                             .withSubject(subject)
                                             .withHTMLText(template)
                                             .buildEmail()

        builder.sendMail(simpleEmail, true)
    }

    override fun resetPasswordTemplate(securityToken: String) {
        template = "<a href='${domain}/reset-password?hash=${securityToken}'>Reset Password</a>"
    }
}