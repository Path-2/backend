package ao.path2.core.repository;

import ao.path2.core.models.Role
import org.springframework.data.repository.PagingAndSortingRepository

interface RoleRepository : PagingAndSortingRepository<Role, Long> {
}