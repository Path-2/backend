package ao.path2.ms.user.core.repository

import ao.path2.ms.user.core.domain.Role

interface RoleRepository {
  fun findByName(name: String): Role?
  fun save(role: Role): Role
}