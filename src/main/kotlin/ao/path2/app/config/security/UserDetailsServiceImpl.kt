package ao.path2.app.config.security

import ao.path2.app.core.domain.Privilege
import ao.path2.app.core.domain.Role
import ao.path2.app.core.repository.RoleRepository
import ao.path2.app.core.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(
  private val userRepo: UserRepository, private val roleRepo: RoleRepository
) : UserDetailsService {
  override fun loadUserByUsername(username: String): UserSecurity? {
    // Create a method in your repo to find a user by its username
    val user = userRepo.findByUsername(username)

    return UserSecurity(
      user.id,
      user.email,
      user.password,
      getAuthorities(user.roles)
    )
  }

  private fun getAuthorities(
    roles: List<Role>
  ): List<GrantedAuthority> {
    return getGrantedAuthorities(getPrivileges(roles))
  }

  private fun getPrivileges(roles: Collection<Role>): List<String> {
    val privileges: MutableList<String> = ArrayList()
    val collection: MutableList<Privilege> = ArrayList()
    for (role in roles) {
      privileges.add(role.name)
      collection.addAll(role.privileges)
    }
    for (item in collection) {
      privileges.add(item.name)
    }
    return privileges
  }

  private fun getGrantedAuthorities(privileges: List<String>): List<GrantedAuthority> {
    val authorities: MutableList<GrantedAuthority> = ArrayList()
    for (privilege in privileges) {
      authorities.add(SimpleGrantedAuthority(privilege))
    }
    return authorities
  }
}