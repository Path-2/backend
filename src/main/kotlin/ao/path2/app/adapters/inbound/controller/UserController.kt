package ao.path2.app.adapters.inbound.controller

import ao.path2.app.adapters.inbound.dto.request.UserDTO
import ao.path2.app.core.domain.User
import ao.path2.app.core.service.UserService
import ao.path2.app.utils.mapping.Mapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RequestMapping("/api/v1/users")
@RestController
@CrossOrigin("*")
class UserController(private val service: UserService, private val mapper: Mapper) {
  @GetMapping
  fun getAll() = service.listAll()

  @PostMapping
  fun save(@RequestBody @Valid user: UserDTO) =
    ResponseEntity.created(URI.create("/api/v1/users")).body(mapper.map(service.save(mapper.map(user, User()) as User), ao.path2.app.adapters.inbound.dto.response.UserDTO()))

  @PatchMapping("/{username}")
  fun update(@RequestBody @Valid user: User, @PathVariable("username") username: String): ResponseEntity<out Any> {
    if (username != user.username)
      return ResponseEntity.badRequest().body("Path id $username and user id ${user.username} is not equal")
    return ResponseEntity.ok(service.update(user))
  }

  @GetMapping("/{username}")
  fun getUser(@PathVariable username: String) = service.findByUsername(username)
}