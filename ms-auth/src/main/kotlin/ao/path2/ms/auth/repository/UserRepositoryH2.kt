package ao.path2.ms.auth.repository

import ao.path2.ms.auth.entity.User
import ao.path2.ms.auth.utils.mapping.Mapper
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(0)
@Component
class UserRepositoryH2(private val repository: UserRepositorySpringData) : UserRepository {
  override fun findByEmail(email: String): User = repository.findByEmail(email)

  override fun findByFacebookId(facebookId: String): User = repository.findByFacebookId(facebookId)

  override fun findByUsername(username: String): User = repository.findByUsername(username)

  override fun exists(id: Long): Boolean {
    if (id == 0L)
      return false
    return repository.existsById(id)
  }

  override fun existsByEmail(email: String): Boolean = repository.existsByEmailIgnoreCase(email)

  override fun existsByFacebookId(facebookId: String): Boolean = repository.existsByFacebookId(facebookId)

  override fun existsByUsername(username: String): Boolean = repository.existsByUsername(username)

}