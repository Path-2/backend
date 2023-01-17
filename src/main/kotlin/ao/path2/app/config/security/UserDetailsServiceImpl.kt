package ao.path2.app.config.security

import ao.path2.app.core.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailsServiceImpl(
  private val repository: UserRepository
) : UserDetailsService {
  override fun loadUserByUsername(username: String): UserDetails {
    // Create a method in your repo to find a user by its username
    val user = repository.findByEmail(username)

    return UserSecurity(
      user.id,
      user.email,
      user.password,
      Collections.singleton(SimpleGrantedAuthority("user"))
    )
  }
}