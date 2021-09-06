package com.pow.services

import com.pow.config.SmtpConfig
import com.pow.mailer.PuppetMailer
import com.pow.models.ForgotAuthToken
import com.pow.models.User
import com.pow.repositories.ForgotAuthTokenRepository
import com.pow.repositories.UserRepository
import com.pow.utils.Utils
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class UserService {
    @Inject lateinit var userRepository: UserRepository
    @Inject lateinit var forgotAuthTokenRepository: ForgotAuthTokenRepository
    @Inject lateinit var smtpConfig: SmtpConfig

    fun findById(id: UUID): Optional<User> = userRepository.findById(id)

    fun findByToken(token: String) = userRepository.findByToken(token)

    fun findByEmailAndPassword(email: String, password: String) = userRepository.findByEmailAndPassword(email, password)

    fun findByEmail(email: String) = userRepository.findByEmail(email)

    fun updatePasswordWithForgotAuthToken(password: String, token: String) : User? {
        val forgotAuthToken = forgotAuthTokenRepository.findByToken(token) ?: return null
        val user = forgotAuthToken.user

        user.password = Utils.getGeneratedPasswordHash(password)
        // [TODO] Should be changed this logic.
        forgotAuthToken.token = Utils.generateKey(32)

        return user
    }

    fun save(user: User): User? {
        userRepository.findByEmail(user.email) ?: return userRepository.save(user)
        return null
    }

    fun sendResetPasswordEmail(user: User) {
        val forgotAuthToken: ForgotAuthToken

        if (user.forgotAuthToken == null) {
            forgotAuthToken = ForgotAuthToken(Utils.generateKey(32), user)
            forgotAuthTokenRepository.save(forgotAuthToken)
        } else {
            forgotAuthToken = user.forgotAuthToken!!
            forgotAuthToken.token = Utils.generateKey(32)
        }

        val mail = PuppetMailer(smtpConfig)
        mail.resetPasswordTemplate(forgotAuthToken.token)
        mail.sendMail(user.email,user.name, "Reset Password [PuppetAPI]")
    }

    fun update(user: User) = userRepository.update(user)
}