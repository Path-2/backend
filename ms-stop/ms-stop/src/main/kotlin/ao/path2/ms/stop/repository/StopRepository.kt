package ao.path2.ms.stop.repository;

import ao.path2.ms.stop.models.Stop
import org.springframework.data.repository.PagingAndSortingRepository

interface StopRepository : PagingAndSortingRepository<Stop, Long> {
}