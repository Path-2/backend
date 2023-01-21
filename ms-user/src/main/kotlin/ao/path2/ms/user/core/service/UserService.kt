package ao.path2.ms.user.core.service

import ao.path2.ms.user.core.domain.PageQuery
import ao.path2.ms.user.core.domain.User

interface UserService {
    fun save(user: User): User
    fun findByEmail(email: String): User
    fun findByPhone(phone: String): User
    fun listAll(pageQuery: PageQuery): List<User>
    fun update(user: User): User
    fun findByUsername(username: String): User
}