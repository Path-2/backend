package ao.path2.ms.auth.repository

import ao.path2.ms.auth.models.User

interface UserRepository {
    fun findByEmail(email: String): User
    fun findByFacebookId(facebookId: String): User
    fun findByUsername(username: String): User
    fun exists(id: Long): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByFacebookId(facebookId: String): Boolean
    fun existsByUsername(username: String): Boolean
}