package ao.path2.ms.user.adapters.outbound.repository

import ao.path2.ms.user.core.domain.PageQuery
import ao.path2.ms.user.core.domain.User
import ao.path2.ms.user.core.repository.UserRepository
import ao.path2.ms.user.utils.mapping.Mapper
import org.springframework.core.annotation.Order
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Order(0)
@Component
class UserRepositoryH2(private val repository: UserRepositorySpringData, private val mapper: Mapper) : UserRepository {
  override fun save(user: User): User {

    repository.save(mapper.map(user,
      ao.path2.ms.user.adapters.outbound.repository.entity.User()
    ) as ao.path2.ms.user.adapters.outbound.repository.entity.User
    )

    return user
  }

  override fun findByEmail(email: String): User = mapper.map(repository.findByEmail(email), User()) as User

  override fun findByPhone(phone: String): User = mapper.map(repository.existsByPhone(phone), User()) as User

  override fun findByUsername(username: String): User = mapper.map(repository.findByUsername(username), User()) as User

  override fun listAll(pageQuery: PageQuery): List<User> {

    return repository
      .findAll(Pageable.ofSize(pageQuery.pageSize).withPage(pageQuery.pageNumber))
      .map { entity -> mapper.map(entity, User()) as User }
      .toList()
  }

  override fun exists(id: Long): Boolean {
    if (id == 0L)
      return false
    return repository.existsById(id)
  }

  override fun existsByEmail(email: String): Boolean = repository.existsByEmailIgnoreCase(email)


  override fun existsByPhone(phone: String): Boolean = repository.existsByPhone(phone)


  override fun existsByUsername(username: String): Boolean = repository.existsByUsername(username)
  override fun existsByFacebookId(username: String): Boolean = repository.existsByFacebookId(username)

  override fun findByFacebookId(username: String): User = mapper.map(repository.findByFacebookId(username), User()) as User

}