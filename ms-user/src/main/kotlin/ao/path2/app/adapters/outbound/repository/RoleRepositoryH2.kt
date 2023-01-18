package ao.path2.app.adapters.outbound.repository

import ao.path2.app.core.domain.Role
import ao.path2.app.core.exceptions.ResourceNotFoundException
import ao.path2.app.core.repository.RoleRepository
import ao.path2.app.utils.mapping.Mapper
import org.springframework.stereotype.Component

@Component
class RoleRepositoryH2(private val repo: RoleRepositorySpringData, private val mapper: Mapper) : RoleRepository {

  override fun findByName(name: String): Role {

    val role = repo.findByName(name) ?: throw ResourceNotFoundException("Role $name is not found")

    return mapper.map(role, Role()) as Role
  }

  override fun save(role: Role): Role {

    repo.findByName(role.name) ?: throw ResourceNotFoundException("Role ${role.name} is not found")

    repo.save(mapper.map(role, ao.path2.app.adapters.outbound.repository.entity.Role()) as ao.path2.app.adapters.outbound.repository.entity.Role)

    return role
  }

}