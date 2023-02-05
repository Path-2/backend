package ao.path2.core.repository;

import ao.path2.core.models.User
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : PagingAndSortingRepository<User, Long> {
}