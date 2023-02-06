package ao.path2.ms.stop.repository;

import ao.path2.ms.stop.models.Stop
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface StopRepository : PagingAndSortingRepository<Stop, Long> {
  fun existsByName(name: String): Boolean

  @Query("select s from Stop s")
  fun findNear(lat: Double, lon: Double): List<Stop>

}