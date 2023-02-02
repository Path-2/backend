package ao.path2.core.repository;

import ao.path2.core.models.Stop
import org.springframework.data.repository.PagingAndSortingRepository

interface StopRepository : PagingAndSortingRepository<Stop, Long> {
}