package ao.path2.ms.stop.repository;

import ao.path2.ms.stop.models.Stop
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface StopRepository : PagingAndSortingRepository<Stop, Long> {
  fun existsByName(name: String): Boolean

  @Query(value = "select * from tb_stop where ST_DistanceSphere(geom, ST_Point(:lon, :lat)) < :distance", nativeQuery = true)
  fun findNear(lon: Double, lat: Double, distance: Double?): List<Stop>

}