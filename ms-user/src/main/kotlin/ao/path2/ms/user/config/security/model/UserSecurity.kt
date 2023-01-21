package ao.path2.ms.user.config.security.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserSecurity(
  val id: Long,
  val email: String,
  private val uPassword: String,
  private val uAuthorities: List<GrantedAuthority>
) : UserDetails {
  override fun getAuthorities() = uAuthorities
  override fun getPassword() = uPassword
  override fun getUsername() = email
  override fun isAccountNonExpired() = true
  override fun isAccountNonLocked() = true
  override fun isCredentialsNonExpired()= true
  override fun isEnabled() = true
}