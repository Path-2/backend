package ao.path2.app.adapters.outbound.repository

import ao.path2.app.adapters.outbound.repository.entity.UserEntity
import ao.path2.app.core.domain.User
import ao.path2.app.core.repository.UserRepository
import ao.path2.app.utils.mapping.Mapper
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(0)
@Component
class UserRepositoryH2(private val repository: UserRepositorySpringData, private val mapper: Mapper) : UserRepository{
  override fun save(user: User): User {

    user.id = repository.save(mapper.map(user, UserEntity()) as UserEntity).id

    return user
  }

  override fun findByEmail(email: String): User {
    TODO("Not yet implemented")
  }

  override fun findByPhone(phone: String): User {
    TODO("Not yet implemented")
  }

  override fun findByUsername(username: String): User {
    TODO("Not yet implemented")
  }

  override fun listAll(): List<User> {
    TODO("Not yet implemented")
  }

  override fun exists(id: Long?): Boolean {
    TODO("Not yet implemented")
  }

  override fun existsByEmail(email: String): Boolean {
    TODO("Not yet implemented")
  }

  override fun existsByPhone(phone: String): Boolean {
    TODO("Not yet implemented")
  }

  override fun existsByUsername(username: String): Boolean {
    TODO("Not yet implemented")
  }
}