package ao.path2.ms.forgot.controller

import ao.path2.ms.forgot.annotations.AuthorizeUser
import ao.path2.ms.forgot.dto.ForgotUserDto
import ao.path2.ms.forgot.dto.PwdDto
import ao.path2.ms.forgot.service.ForgotService
import ao.path2.ms.user.config.security.model.UserSecurity
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/forgot")
@RestController
class ForgotController(private val forgotService: ForgotService) {

  @PostMapping
  fun createRequestToRestorePwd(@RequestBody user: ForgotUserDto): ResponseEntity<Unit> {
    forgotService.createRequestToRestorePwd(user)
    return ResponseEntity.ok().build()
  }

  @AuthorizeUser
  @PatchMapping("/new-password")
  fun updatePwd(@RequestBody pwdDto: PwdDto): ResponseEntity<Unit> {

    val username = (SecurityContextHolder.getContext().authentication.principal as UserSecurity).username

    forgotService.updatePwd(pwdDto, username)

    return ResponseEntity.ok().build()
  }
}