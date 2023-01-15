package ao.path2.app.core.repository

import ao.path2.app.core.domain.User

interface UserRepository {
    fun save(user: User): User
    fun findByEmail(email: String): User
    fun findByPhone(phone: String): User
    fun findByUsername(username: String): User
    fun listAll(): List<User>
    fun exists(id: Long?): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByPhone(phone: String): Boolean
    fun existsByUsername(username: String): Boolean
}