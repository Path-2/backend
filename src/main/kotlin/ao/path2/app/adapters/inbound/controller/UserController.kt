package ao.path2.app.adapters.inbound.controller

import ao.path2.app.core.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/users")
@RestController
class UserController {
    @GetMapping
    fun listAll() = ResponseEntity.ok("")

    @PostMapping
    fun save(user: User) = ResponseEntity.ok(user)
}