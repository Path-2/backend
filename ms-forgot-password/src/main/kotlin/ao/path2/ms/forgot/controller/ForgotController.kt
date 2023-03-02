package ao.path2.ms.forgot.controller

import ao.path2.ms.forgot.dto.ForgotUserDto
import ao.path2.ms.forgot.dto.PwdDto
import ao.path2.ms.forgot.service.ForgotService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/forgot")
@RestController
class ForgotController(private val forgotService: ForgotService) {

  @PostMapping
  fun createRequestToRestorePwd(@RequestBody user: ForgotUserDto): ResponseEntity<Unit> {
    forgotService.createRequestToRestorePwd(user)
    return ResponseEntity.ok().build()
  }

  @PreAuthorize("hasRole('ROLE_USER')")
  @PatchMapping("/new-password")
  fun updatePwd(@RequestBody pwdDto: PwdDto, @PathVariable token: String): ResponseEntity<Unit> {

    forgotService.updatePwd(pwdDto, token)

    return ResponseEntity.ok().build()
  }
}