package ao.path2.ms.stop.service

import ao.path2.ms.stop.dto.StatusDto
import ao.path2.ms.stop.models.Stop
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface StopService {
  fun findAll(page: Pageable, status: Boolean): Page<Stop>
  fun save(stop: Stop): Stop
  fun findById(id: Long): Stop
  fun update(stop: Stop): Stop
  fun delete(id: Long)
  fun findStopsNear(lat: Double, lon: Double, distance: String): List<Stop>
  fun status(id: Long, statusDto: StatusDto): Stop
}