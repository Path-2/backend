package ao.path2.ms.stop.controller

import ao.path2.ms.stop.models.Stop
import ao.path2.ms.stop.service.StopService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/v1/stops")
@RestController
class StopController(private val service: StopService) {

  fun findAll(@PageableDefault(page = 0, size = 20) page: Pageable): ResponseEntity<Page<Stop>> =
    ResponseEntity.ok(service.findAll(page))

  fun save(stop: Stop): ResponseEntity<Stop> = ResponseEntity.status(HttpStatus.CREATED).body(service.save(stop))

  fun findById(id: Long): ResponseEntity<Stop> = ResponseEntity.ok(service.findById(id))

  fun findStopsNear(lat: Double, lon: Double, distance: Double?): ResponseEntity<List<Stop>> = ResponseEntity.ok(
    service.findStopsNear(
      lat, lon, distance
    )
  )

  fun update(stop: Stop): ResponseEntity<Stop> = ResponseEntity.ok(service.update(stop))

  fun disable(id: Long) = ResponseEntity.ok(service.disable(id))

  fun enable(id: Long) = ResponseEntity.ok(service.enable(id))

  fun delete(id: Long) = ResponseEntity.ok(service.delete(id))
}