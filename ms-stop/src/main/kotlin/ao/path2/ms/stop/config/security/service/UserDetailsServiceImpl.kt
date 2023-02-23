package ao.path2.ms.stop.config.security.service

import ao.path2.core.exceptions.ResourceNotFoundException
import ao.path2.ms.stop.config.security.model.UserSecurity
import ao.path2.ms.stop.models.Role
import ao.path2.ms.stop.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserDetailsServiceImpl(
  private val userRepo: UserRepository
) : UserDetailsService {
  @Transactional
  override fun loadUserByUsername(username: String): UserSecurity? {
    // Create a method in your repo to find a user by its username
    val user = if (userRepo.existsByUsername(username))
      userRepo.findByUsername(username)
    else if (userRepo.existsByFacebookId(username))
      userRepo.findByFacebookId(username)
    else if (userRepo.existsByEmail(username))
      userRepo.findByEmail(username)
    else throw ResourceNotFoundException("User with username or email: $username not found")

    return UserSecurity(
      user.id,
      user.email,
      user.password!!,
      getAuthorities(user.roles!!)
    )
  }

  private fun getAuthorities(
    roles: List<Role>
  ): List<GrantedAuthority> {
    return roles.map { role -> SimpleGrantedAuthority(role.name) }
  }
}