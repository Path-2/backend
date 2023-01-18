package ao.path2.app.core.repository

import ao.path2.app.core.domain.Role

interface RoleRepository {
  fun findByName(name: String): Role?
  fun save(role: Role): Role
}