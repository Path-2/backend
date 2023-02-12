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
import org.springframework.beans.BeanUtils
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
        throw ExceedMaxValueException("lon is greater than 180 degree or lesser than -180 degree")
    }

    if (lat != null) {
      if (lat >= 90 || lat <= -90)
        throw ExceedMaxValueException("lat is greater than 90 degree or lesser than -90 degree")
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

    val numberRegex = Regex.fromLiteral("[0-9]+([.][0-9]+){0,}")

    if (!distance.endsWith("Km") || !distance.endsWith("m"))
      throw IllegalArgumentException("You send an unit unexpected ${distance.replace(numberRegex, "")}")

    if (lon >= 180 || lon <= -180)
      throw ExceedMaxValueException("lon is greater than 180 degree or lesser than -180 degree")

    if (lat >= 90 || lat <= -90)
      throw ExceedMaxValueException("lat is greater than 90 degree or lesser than -90 degree")

    return repository.findNear(point, convertStringDistanceToDouble(dist))
  }

  override fun update(stop: Stop, id: Long): Stop {
    if (stop.id == null)
      throw ResourceNotFoundException("Fails to try update a resource without id")

    val s = repository.findById(id).get()

    if (s.enabled != stop.enabled)
      throw IllegalStateException("You cannot update this field [enabled]")

    if (s.verified != stop.verified)
      throw IllegalStateException("You cannot update this field [verified]")

    if (id != stop.id)
      throw RuntimeException("You try update $id through ${stop.id}")

    BeanUtils.copyProperties(stop, s)

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
    if (distance.endsWith("Km"))
      distance.replace("Km", "").toDouble() * 1000
    else
      distance.replace("m", "").toDouble()
}