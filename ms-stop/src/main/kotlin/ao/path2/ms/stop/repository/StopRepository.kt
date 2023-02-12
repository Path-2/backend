package ao.path2.ms.stop.repository;

import ao.path2.ms.stop.models.Stop
import org.locationtech.jts.geom.Point
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface StopRepository : PagingAndSortingRepository<Stop, Long> {
  fun existsByName(name: String): Boolean

  @Query(value = "select * from tb_stop where ST_Distance_Sphere(point, :point) < :distance", nativeQuery = true)
  fun findNear(point: Point?, distance: Double?): List<Stop>

  fun findAllByEnabled(enabled: Boolean, pageable: Pageable): Page<Stop>

}