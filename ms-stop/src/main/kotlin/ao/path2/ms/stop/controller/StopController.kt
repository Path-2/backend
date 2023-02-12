package ao.path2.ms.stop.controller

import ao.path2.ms.stop.dto.StatusDto
import ao.path2.ms.stop.models.Stop
import ao.path2.ms.stop.service.StopService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/v1/stops")
@RestController
class StopController(private val service: StopService) {

  @GetMapping
  fun findAll(
    @PageableDefault(page = 0, size = 20) page: Pageable,
    @RequestParam(defaultValue = "true", name = "status") status: Boolean
  ): ResponseEntity<Page<Stop>> =
    ResponseEntity.ok(service.findAll(page, status))

  @PostMapping
  fun save(@Valid @RequestBody stop: Stop): ResponseEntity<Stop> =
    ResponseEntity
      .status(HttpStatus.CREATED)
      .body(service.save(stop))

  @GetMapping(path = ["/{id}"])
  fun findById(@PathVariable id: Long): ResponseEntity<Stop> = ResponseEntity.ok(service.findById(id))

  @GetMapping(path = ["/near"], params = ["lat", "lon"])
  fun findStopsNear(
    @RequestParam(name = "lat", required = true) lat: Double,
    @RequestParam(name = "lon", required = true) lon: Double,
    @RequestParam(name = "distance", required = false, defaultValue = "1000m") distance: String
  ): ResponseEntity<List<Stop>> = ResponseEntity.ok(
    service.findStopsNear(
      lat, lon, distance
    )
  )

  @PutMapping("/{id}")
  fun update(@RequestBody stop: Stop, @PathVariable id: Long): ResponseEntity<Stop> = ResponseEntity.ok(service.update(stop, id))

  @PatchMapping("/{id}/status")
  fun disable(@PathVariable id: Long, statusDto: StatusDto) = ResponseEntity.ok(service.status(id, statusDto))

  @DeleteMapping("/{id}")
  fun delete(@PathVariable id: Long) = ResponseEntity.ok(service.delete(id))
}