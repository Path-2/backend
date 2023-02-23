package ao.path2.ms.user.repository;

import ao.path2.ms.user.models.Role
import org.springframework.data.repository.PagingAndSortingRepository

interface RoleRepository : PagingAndSortingRepository<Role, Long> {
  fun findByName(name: String): Role?
  fun findByNameContainingIgnoreCase(name: String): Role?
  fun save(role: Role): Role
}