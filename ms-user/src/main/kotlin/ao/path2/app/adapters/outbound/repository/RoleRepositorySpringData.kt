package ao.path2.app.adapters.outbound.repository

import ao.path2.app.adapters.outbound.repository.entity.Role
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepositorySpringData: PagingAndSortingRepository<Role, Long> {

  fun findByName(name: String): Role?

}