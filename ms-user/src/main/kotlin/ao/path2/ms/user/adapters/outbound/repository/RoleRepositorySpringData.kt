package ao.path2.ms.user.adapters.outbound.repository

import ao.path2.ms.user.adapters.outbound.repository.entity.Role
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepositorySpringData: PagingAndSortingRepository<Role, Long> {

  fun findByName(name: String): Role?

}