package ao.path2.ms.auth.repository

import ao.path2.ms.auth.models.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositorySpringData : PagingAndSortingRepository<User, Long> {

  fun existsByEmailIgnoreCase(email: String): Boolean
  fun existsByFacebookId(facebookId: String): Boolean
  fun existsByUsername(username: String): Boolean


  fun findByEmail(email: String): User
  fun findByFacebookId(facebookId: String): User
  fun findByUsername(username: String): User
}