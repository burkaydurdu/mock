package com.pow.mailer

interface PuppetMail {
    fun setBuilder()
    fun sendMail(email: String, name: String, subject: String)
    fun resetPasswordTemplate(securityToken: String)
}