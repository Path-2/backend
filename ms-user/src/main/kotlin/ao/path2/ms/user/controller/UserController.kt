package ao.path2.ms.user.controller

import ao.path2.ms.user.models.User
import ao.path2.ms.user.dto.UserDTO
import ao.path2.ms.user.service.UserService
import ao.path2.ms.user.utils.mapping.Mapper
import ao.path2.token.JwtToken
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RequestMapping("/v1/users")
@RestController
@CrossOrigin("*")
class UserController(private val service: UserService, private val mapper: Mapper, private val jwt: JwtToken) {
  @GetMapping
  fun getAll(@PageableDefault(size = 15, page = 0) page: Pageable): ResponseEntity<Page<User>> {

    return ResponseEntity.ok(service.listAll(page))
  }

  @PostMapping
  fun save(@RequestBody @Valid user: UserDTO): ResponseEntity<Any> {

    val userSaved = service.save(mapper.map(user, User()) as User)

    val token = jwt.generateToken(userSaved.username)

    return ResponseEntity.created(URI.create("/api/v1/users")).header("token", token).body("")
  }

  @PatchMapping("/{username}")
  fun update(@RequestBody @Valid user: User, @PathVariable("username") username: String): ResponseEntity<out Any> {
    if (username != user.username)
      return ResponseEntity.badRequest().body("Path username $username and user username ${user.username} is not equal")
    return ResponseEntity.ok(service.update(user))
  }

  @GetMapping("/{username}")
  fun getUser(@PathVariable username: String) = ResponseEntity.ok(
    mapper.map(
      service.findByUsername(username),
      UserDTO(),
      transform = { data -> (data as UserDTO).password = null }
    )
  )
}