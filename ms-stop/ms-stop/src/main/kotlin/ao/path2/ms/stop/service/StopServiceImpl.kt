package ao.path2.ms.stop.service

import ao.path2.core.exceptions.ResourceExistsException
import ao.path2.ms.stop.models.Stop
import ao.path2.ms.stop.repository.StopRepository
import ao.path2.core.exceptions.ResourceNotFoundException
import ao.path2.ms.stop.dto.StatusDto
import ao.path2.ms.stop.exceptions.ExceedMaxValueException
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StopServiceImpl(private val repository: StopRepository) : StopService {
  override fun findAll(page: Pageable, status: Boolean): Page<Stop> = repository.findAllByEnabled(status, page)

  override fun save(stop: Stop): Stop {
    if (repository.existsByName(stop.name))
      throw ResourceExistsException("")

    val lon = stop.point?.x
    val lat = stop.point?.y

    if (lon != null) {
      if (lon >= 180 || lon <= -180)
        throw ExceedMaxValueException("lon is greater 180 degree or lesser -180 degree")
    }

    if (lat != null) {
      if (lat >= 90 || lat <= -90)
        throw ExceedMaxValueException("lat is greater 90 degree or lesser -90 degree")
    }

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
    if (stop.id == null)
      throw ResourceNotFoundException("Fails to try update a resource without id")

    stop.updatedAt = LocalDateTime.now()

    return repository.save(stop)
  }

  override fun status(id: Long, statusDto: StatusDto): Stop {
    val stop = repository.findById(id).orElseThrow { ResourceNotFoundException("Stop not found $id") }

    stop.enabled = statusDto.status

    return repository.save(stop)
  }

  override fun delete(id: Long) {
    if (!repository.existsById(id))
      throw ResourceNotFoundException("Stop not found $id")

    repository.deleteById(id)
  }

  private fun convertStringDistanceToDouble(distance: String): Double =
    if (distance.matches(Regex.fromLiteral("[0-9]+Km")))
      distance.replace("Km", "").toDouble() * 1000
    else
      distance.replace("m", "").toDouble()
}