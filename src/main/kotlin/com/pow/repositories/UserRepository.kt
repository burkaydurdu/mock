package com.pow.repositories

import com.pow.models.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, UUID> {
    fun findByToken(token: String): Optional<User>
    fun findByEmailAndPassword(email: String, password: String): User?
    fun findByEmail(email: String): User?
    fun save(user: User): User
    fun update(user: User): User
}