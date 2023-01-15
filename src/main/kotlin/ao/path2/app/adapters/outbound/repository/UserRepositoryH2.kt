package ao.path2.app.adapters.outbound.repository

import ao.path2.app.adapters.outbound.repository.entity.UserEntity
import ao.path2.app.core.domain.PageQuery
import ao.path2.app.core.domain.User
import ao.path2.app.core.repository.UserRepository
import ao.path2.app.utils.mapping.Mapper
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties
import org.springframework.core.annotation.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Order(0)
@Component
class UserRepositoryH2(private val repository: UserRepositorySpringData, private val mapper: Mapper) : UserRepository {
  override fun save(user: User): User {

    repository.save(mapper.map(user, UserEntity()) as UserEntity)

    return user
  }

  override fun findByEmail(email: String): User {
    TODO("Not yet implemented")
  }

  override fun findByPhone(phone: String): User {
    TODO("Not yet implemented")
  }

  override fun findByUsername(username: String): User = mapper.map(repository.findByUsername(username), User()) as User

  override fun listAll(pageQuery: PageQuery): List<User> {

    return repository
      .findAll(Pageable.ofSize(pageQuery.pageSize))
      .map { entity -> mapper.map(entity, User()) as User }
      .toList()
  }

  override fun exists(id: Long?): Boolean {
    if (id == null)
      return false
    return repository.existsById(id)
  }

  override fun existsByEmail(email: String): Boolean = repository.existsByEmailIgnoreCase(email)


  override fun existsByPhone(phone: String): Boolean = repository.existsByPhone(phone)


  override fun existsByUsername(username: String): Boolean = repository.existsByUsername(username)

}