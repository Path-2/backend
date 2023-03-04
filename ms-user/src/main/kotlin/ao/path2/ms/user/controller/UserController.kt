package ao.path2.ms.user.controller

import ao.path2.ms.user.annotation.AuthorizeOwner
import ao.path2.ms.user.annotation.AuthorizeUser
import ao.path2.ms.user.core.exceptions.UnSupportedSocialNetworking
import ao.path2.ms.user.dto.UserDTO
import ao.path2.ms.user.models.SocialDto
import ao.path2.ms.user.models.User
import ao.path2.ms.user.models.enums.UserSource
import ao.path2.ms.user.service.UserService
import ao.path2.ms.user.token.JwtToken
import ao.path2.ms.user.utils.mapping.Mapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/v1/users")
@RestController
@CrossOrigin("*")
class UserController(private val service: UserService, private val mapper: Mapper, private val jwt: JwtToken) {
  @AuthorizeUser
  @GetMapping
  fun getAll(@PageableDefault(size = 50, page = 0) page: Pageable): ResponseEntity<Page<User>> {

    return ResponseEntity.ok(service.listAll(page))
  }

  @PostMapping("/signup")
  fun save(@RequestBody @Valid user: UserDTO): ResponseEntity<Any> {

    val userSaved = service.save(mapper.map(user, User()) as User)

    val token = jwt.generateToken(userSaved.username)

    return ResponseEntity.status(HttpStatus.CREATED).header("token", token).body("")
  }

  @PostMapping("/signup/social")
  fun saveWithSocialLogin(@RequestBody @Valid data: SocialDto): ResponseEntity<Any> {

    val userSaved: User = when (data.type) {
      UserSource.GOOGLE -> service.signupWithGoogle(data.token)
      UserSource.FACEBOOK -> service.signupWithFacebook(data.token)
      else -> throw UnSupportedSocialNetworking(data.type.name)
    }

    val token = jwt.generateToken(userSaved.username)

    return ResponseEntity.status(HttpStatus.CREATED).header("token", token).body("")
  }

  @AuthorizeOwner
  @PatchMapping("/{username}")
  fun update(@RequestBody @Valid user: User, @PathVariable("username") username: String): ResponseEntity<out Any> {
    if (username != user.username) return ResponseEntity.badRequest()
      .body("Path username $username and user username ${user.username} is not equal")

    return ResponseEntity.ok(service.update(user))
  }

  @AuthorizeUser
  @GetMapping("/{username}")
  fun getUser(@PathVariable username: String) =
    ResponseEntity.ok(mapper.map(service.findByUsername(username), UserDTO(), transform = { data ->
      run {
        val user = data as UserDTO
        user.password = null
        user.facebookId = null
      }
    }))
}