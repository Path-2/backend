package ao.path2.ms.user.service

import ao.path2.ms.user.models.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    fun save(user: User): User
    fun findByEmail(email: String): User
    fun findByPhone(phone: String): User
    fun listAll(page: Pageable): Page<User>
    fun update(user: User): User
    fun findByUsername(username: String): User
}