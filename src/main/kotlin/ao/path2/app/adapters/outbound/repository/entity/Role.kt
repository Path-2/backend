package ao.path2.app.adapters.outbound.repository.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "TB_ROLE")
data class Role(
  @GeneratedValue
  @Id val id: Long = 0,
  @NotNull
  var name: String,
  @ManyToMany(mappedBy = "roles")
  var users: List<User> = listOf(),
  @ManyToMany
  @JoinTable(
    name = "roles_privileges",
    joinColumns = [JoinColumn(
      name = "role_id", referencedColumnName = "id"
    )],
    inverseJoinColumns = [JoinColumn(
      name = "privilege_id", referencedColumnName = "id"
    )]
  )
  var privileges: List<Privilege> = listOf()
)
