package ao.path2.app.core.repository

import ao.path2.app.core.domain.Privilege

interface PrivilegeRepository {
  fun findByName(name: String): Privilege?
  fun save(privilege: Privilege)

}
