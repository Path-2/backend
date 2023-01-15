package ao.path2.app.core.service

import ao.path2.app.core.domain.User

interface UserService {
    fun save(user: User): User
    fun findByEmail(email: String): User
    fun findByPhone(phone: String): User
    fun listAll(): List<User>
    fun update(user: User): User
    fun findByUsername(username: String): User
}