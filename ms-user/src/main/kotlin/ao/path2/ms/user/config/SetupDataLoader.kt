package ao.path2.ms.user.config

import ao.path2.ms.user.core.domain.Role
import ao.path2.ms.user.core.repository.RoleRepository
import ao.path2.ms.user.core.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
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
    println("Config......")
    alreadySetup = true
  }

  @Transactional
  fun createRoleIfNotFound(name: String) {
    var role = roleRepository.findByName(name);
    if (role == null) {
      role = Role(name)

      roleRepository.save(role)
    }
  }
}