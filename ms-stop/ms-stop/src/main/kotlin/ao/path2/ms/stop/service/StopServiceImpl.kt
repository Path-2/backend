package ao.path2.ms.stop.service

import ao.path2.core.exceptions.ResourceExistsException
import ao.path2.ms.stop.models.Stop
import ao.path2.ms.stop.repository.StopRepository
import ao.path2.ms.user.core.exceptions.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StopServiceImpl(private val repository: StopRepository): StopService {
  override fun findAll(page: Pageable): Page<Stop> = repository.findAll(page)

  override fun save(stop: Stop): Stop {
    if (repository.existsByName(stop.name))
      throw ResourceExistsException("")

    return repository.save(stop)
  }

  override fun findById(id: Long): Stop {
    return repository.findById(id).orElseThrow { ResourceNotFoundException("") }
  }

  override fun findStopsNear(lat: Double, lon: Double): List<Stop> {
    return repository.findNear(lat, lon)
  }

  override fun update(stop: Stop): Stop {
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
}