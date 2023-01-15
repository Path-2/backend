package ao.path2.app.adapters.outbound.repository

import ao.path2.app.adapters.outbound.repository.entity.UserEntity
import ao.path2.app.core.repository.UserRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepositorySpringData: PagingAndSortingRepository<UserEntity, Long>{
}