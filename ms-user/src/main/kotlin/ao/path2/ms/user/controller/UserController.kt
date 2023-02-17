package ao.path2.ms.user.controller

import ao.path2.ms.user.models.User
import ao.path2.ms.user.dto.UserDTO
import ao.path2.ms.user.handlers.ErrorDetails
import ao.path2.ms.user.service.UserService
import ao.path2.ms.user.utils.mapping.Mapper
import ao.path2.ms.user.token.JwtToken
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDateTime
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

  @GetMapping("/signup/social")
  fun saveWithSocialLogin(
    @RequestHeader("facebookToken") facebookToken: String?,
    @RequestHeader("googleToken") googleToken: String?
  ): ResponseEntity<Any> {

    if (facebookToken != null && googleToken != null)
      return ResponseEntity
        .badRequest()
        .body(
          ErrorDetails(
            400,
            "You cannot try signup with two social networking, send only one",
            LocalDateTime.now(),
            "two tokens or more"
          )
        )

    val userSaved: User =
      if (googleToken != null)
        service.signupWithGoogle(googleToken)
      else if (facebookToken != null)
        service.signupWithFacebook(facebookToken)
      else throw NullPointerException()

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
      transform = { data ->
        run {
          val user = data as UserDTO

          user.password = null
          user.facebookId = null
        }
      }
    )
  )
}