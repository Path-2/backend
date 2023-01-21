package ao.path2.ms.user.adapters.inbound.controller

import ao.path2.ms.user.adapters.inbound.dto.UserDTO
import ao.path2.ms.user.core.domain.PageQuery
import ao.path2.ms.user.core.domain.User
import ao.path2.ms.user.core.service.UserService
import ao.path2.ms.user.utils.mapping.Mapper
import ao.path2.token.JwtToken
import org.springframework.data.domain.PageImpl
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
  fun getAll(@PageableDefault(size = 15, page = 0) page: Pageable): ResponseEntity<PageImpl<User>> {
    val pageQuery: PageQuery = PageQuery(page.pageSize, page.pageNumber)

    val pageResponse = PageImpl(service.listAll(pageQuery), page, page.pageSize.toLong())

    return ResponseEntity.ok(pageResponse)
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