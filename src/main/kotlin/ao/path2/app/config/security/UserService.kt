package ao.path2.app.config.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
  private val passwordEncoder: BCryptPasswordEncoder
)