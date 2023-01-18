package ao.path2.app.config

import ao.path2.app.core.domain.Privilege
import ao.path2.app.core.domain.Role
import ao.path2.app.core.repository.PrivilegeRepository
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
  private lateinit var privilegeRepository: PrivilegeRepository

  @Autowired
  private lateinit var passwordEncoder: PasswordEncoder

  @Transactional
  override fun onApplicationEvent(event: ContextRefreshedEvent) {
    if (alreadySetup) return
    val readPrivilege: Privilege = createPrivilegeIfNotFound("READ_PRIVILEGE")
    val writePrivilege: Privilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE")

    val adminPrivileges: List<Privilege> = listOf(
      readPrivilege, writePrivilege
    )
    createRoleIfNotFound("ROLE_ADMIN", adminPrivileges)
    createRoleIfNotFound("ROLE_USER", listOf(readPrivilege))

    alreadySetup = true
  }

  @Transactional
  fun createPrivilegeIfNotFound(name: String): Privilege {
    var privilege = privilegeRepository.findByName(name)

    if (privilege == null) {
      privilege = Privilege(name)
      privilegeRepository.save(privilege)
    }
    return privilege
  }

  @Transactional
  fun createRoleIfNotFound(name: String, privileges: List<Privilege>): Role {

    var role = roleRepository.findByName(name);
    if (role == null) {
      role = Role(name);
      role.privileges = privileges

      roleRepository.save(role);
    }
    return role;
  }
}