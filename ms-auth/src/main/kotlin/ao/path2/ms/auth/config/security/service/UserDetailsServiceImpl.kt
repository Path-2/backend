package ao.path2.ms.auth.config.security.service

import ao.path2.ms.auth.config.security.model.UserSecurity
import ao.path2.ms.auth.exceptions.ResourceNotFoundException
import ao.path2.ms.auth.models.User
import ao.path2.ms.auth.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
  private val userRepo: UserRepository
) : UserDetailsService {
  override fun loadUserByUsername(username: String): UserSecurity? {
    // Create a method in your repo to find a user by its username

    if (username.isEmpty() || username.isBlank())
      throw ResourceNotFoundException("???")

    val user: User = if (userRepo.existsByUsername(username))
      userRepo.findByUsername(username)
    else if (userRepo.existsByFacebookId(username))
      userRepo.findByFacebookId(username)
    else if (userRepo.existsByEmail(username))
      userRepo.findByEmail(username)
    else
      throw Exception()

    var password = user.password

    if (userRepo.existsByFacebookId(username))
      password = ""

    return UserSecurity(
      user.id,
      username,
      user.password,
      listOf()
    )
  }
}