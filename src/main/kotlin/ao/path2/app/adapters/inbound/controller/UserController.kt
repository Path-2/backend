package ao.path2.app.adapters.inbound.controller

import ao.path2.app.core.domain.User
import ao.path2.app.core.exceptions.ResourceNotFoundException
import ao.path2.app.core.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/users")
@RestController
@CrossOrigin("*")
class UserController(private val service: UserService) {
  @GetMapping
  fun getAll() = service.listAll()

  @PostMapping
  fun save(@RequestBody user: User) = service.save(user)

  @PatchMapping("/{username}")
  fun update(@RequestBody user: User, @PathVariable("username") username: String): ResponseEntity<out Any> {
    if (username != user.username)
      return ResponseEntity.badRequest().body("Path id $username and user id ${user.username} is not equal")
    return ResponseEntity.ok(service.update(user))
  }

  @GetMapping("/{username}")
  fun getUser(@PathVariable username: String) = service.findByUsername(username)
}