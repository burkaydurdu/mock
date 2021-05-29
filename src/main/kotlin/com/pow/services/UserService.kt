package com.pow.services

import com.pow.models.User
import com.pow.repositories.UserRepository
import java.util.*
import javax.inject.Inject

class UserService {
    @Inject lateinit var userRepository: UserRepository

    fun findById(id: UUID): Optional<User> = userRepository.findById(id)

    fun findByToken(token: String) = userRepository.findByToken(token)

    fun findByEmailAndPassword(email: String, password: String) = userRepository.findByEmailAndPassword(email, password)

    fun save(user: User): User? {
        userRepository.findByEmail(user.email) ?: return userRepository.save(user)
        return null
    }

    fun update(user: User) = userRepository.update(user)
}