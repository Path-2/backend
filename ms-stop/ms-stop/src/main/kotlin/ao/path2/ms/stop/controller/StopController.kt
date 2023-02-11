package ao.path2.ms.stop.controller

import ao.path2.ms.stop.models.Stop
import ao.path2.ms.stop.service.StopService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/stops")
@RestController
class StopController(private val service: StopService) {

  @GetMapping
  fun findAll(@PageableDefault(page = 0, size = 20) page: Pageable): ResponseEntity<Page<Stop>> =
    ResponseEntity.ok(service.findAll(page))

  @PostMapping
  fun save(@RequestBody stop: Stop): ResponseEntity<Stop> =
    ResponseEntity.status(HttpStatus.CREATED).body(service.save(stop))

  @GetMapping(path = ["/{id}"])
  fun findById(@PathVariable id: Long): ResponseEntity<Stop> = ResponseEntity.ok(service.findById(id))

  @GetMapping(path = ["/near"], params = ["lat", "lon"])
  fun findStopsNear(
    @RequestParam(name = "lat", required = true) lat: Double,
    @RequestParam(name = "lon", required = true) lon: Double,
    @RequestParam(name = "distance", required = false, defaultValue = "1000") distance: Double?
  ): ResponseEntity<List<Stop>> = ResponseEntity.ok(
    service.findStopsNear(
      lat, lon, distance
    )
  )

  @PutMapping("/{id}")
  fun update(@RequestBody stop: Stop): ResponseEntity<Stop> = ResponseEntity.ok(service.update(stop))

  @GetMapping("/disable/{id}")
  fun disable(@PathVariable id: Long) = ResponseEntity.ok(service.disable(id))

  @GetMapping("/enable/{id}")
  fun enable(@PathVariable id: Long) = ResponseEntity.ok(service.enable(id))

  @DeleteMapping("/{id}")
  fun delete(@PathVariable id: Long) = ResponseEntity.ok(service.delete(id))
}