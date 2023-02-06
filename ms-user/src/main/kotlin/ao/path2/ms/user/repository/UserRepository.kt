package ao.path2.ms.user.repository;

import ao.path2.ms.user.models.User
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface UserRepository : PagingAndSortingRepository<User, Long> {
  fun save(user: User): User
  fun findByEmail(email: String): User
  fun findByPhone(phone: String): User
  fun findByUsername(username: String): User
  fun existsByEmail(email: String): Boolean
  fun existsByPhone(phone: String): Boolean
  fun existsByUsername(username: String): Boolean
  fun existsByFacebookId(username: String): Boolean
  fun findByFacebookId(username: String): User
}