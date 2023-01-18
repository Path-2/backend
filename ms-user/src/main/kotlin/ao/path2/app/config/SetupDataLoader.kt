package ao.path2.app.config

import ao.path2.app.core.domain.Role
import ao.path2.app.core.repository.RoleRepository
import ao.path2.app.core.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import javax.transaction.Transactional


class SetupDataLoader : ApplicationListener<ContextRefreshedEvent> {
  private var alreadySetup = false

  @Autowired
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var roleRepository: RoleRepository

  @Autowired
  private lateinit var passwordEncoder: PasswordEncoder

  @Transactional
  override fun onApplicationEvent(event: ContextRefreshedEvent) {
    if (alreadySetup) return

    createRoleIfNotFound("ROLE_ADMIN")
    createRoleIfNotFound("ROLE_USER")

    alreadySetup = true
  }

  @Transactional
  fun createRoleIfNotFound(name: String): Role {
    var role = roleRepository.findByName(name);
    if (role == null) {
      role = Role(name)

      roleRepository.save(role)
    }
    return role;
  }
}