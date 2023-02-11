package ao.path2.core.repository;

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : PagingAndSortingRepository<User, Long> {
  fun save(user: User): User
  fun findByEmail(email: String): User
  fun findByPhone(phone: String): User
  fun findByUsername(username: String): User
  fun listAll(page: Pageable): List<User>
  fun exists(id: Long): Boolean
  fun existsByEmail(email: String): Boolean
  fun existsByPhone(phone: String): Boolean
  fun existsByUsername(username: String): Boolean
  fun existsByFacebookId(username: String): Boolean
  fun findByFacebookId(username: String): User
}