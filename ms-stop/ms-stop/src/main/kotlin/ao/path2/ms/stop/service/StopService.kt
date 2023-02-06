package ao.path2.ms.stop.service

import ao.path2.ms.stop.models.Stop
import org.springframework.data.domain.Page

interface StopService {
  fun findAll(): Page<Stop>
  fun save(stop: Stop): Stop
  fun findById(id: Long): Stop
  fun findStopsNear(lat: Double, lon: Double): List<Stop>
  fun update(stop: Stop): Stop
  fun disable(id: Long)
  fun enable(id: Long)
  fun delete(id: Long)
}