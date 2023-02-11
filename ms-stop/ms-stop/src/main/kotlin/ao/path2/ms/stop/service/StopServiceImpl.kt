package ao.path2.ms.stop.service

import ao.path2.core.exceptions.ResourceExistsException
import ao.path2.ms.stop.models.Stop
import ao.path2.ms.stop.repository.StopRepository
import ao.path2.core.exceptions.ResourceNotFoundException
import org.geolatte.geom.G2D
import org.geolatte.geom.Geometries
import org.geolatte.geom.crs.CoordinateReferenceSystems
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StopServiceImpl(private val repository: StopRepository) : StopService {
  override fun findAll(page: Pageable): Page<Stop> = repository.findAll(page)

  override fun save(stop: Stop): Stop {
    if (repository.existsByName(stop.name))
      throw ResourceExistsException("")

    return repository.save(stop)
  }

  override fun findById(id: Long): Stop {
    return repository.findById(id).orElseThrow { ResourceNotFoundException("") }
  }

  override fun findStopsNear(lat: Double, lon: Double, distance: String): List<Stop> {

    val point = GeometryFactory(PrecisionModel(PrecisionModel.FLOATING), 4326).createPoint(Coordinate(lon, lat))

    val dist = distance.trim()
      .replace("k", "K")
      .replace("M", "m")
      .replace(",", ".")

    return repository.findNear(point, convertStringDistanceToDouble(dist))
  }

  override fun update(stop: Stop): Stop {

    stop.updatedAt = LocalDateTime.now()
    
    return repository.save(stop)
  }

  override fun disable(id: Long) {
    val stop = repository.findById(id).orElseThrow { ResourceNotFoundException("") }

    stop.enabled = false

    repository.save(stop)
  }

  override fun enable(id: Long) {
    val stop = repository.findById(id).orElseThrow { ResourceNotFoundException("") }

    stop.enabled = true

    repository.save(stop)
  }

  override fun delete(id: Long) {
    if (!repository.existsById(id))
      throw ResourceNotFoundException("")

    repository.deleteById(id)
  }

  private fun convertStringDistanceToDouble(distance: String): Double =
    if (distance.matches(Regex.fromLiteral("[0-9]+Km"))) distance.toDouble()
    else distance.toDouble()
}