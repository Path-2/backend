package ao.path2.ms.user.adapters.outbound.repository

import ao.path2.ms.user.adapters.outbound.repository.entity.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositorySpringData : PagingAndSortingRepository<User, Long> {

  fun existsByEmailIgnoreCase(email: String): Boolean
  fun existsByPhone(phone: String): Boolean
  fun existsByUsername(username: String): Boolean
  fun existsByFacebookId(facebookId: String): Boolean
  fun findByFacebookId(facebookId: String): User
  fun findByEmail(email: String): User

  fun findByUsername(username: String): User
}