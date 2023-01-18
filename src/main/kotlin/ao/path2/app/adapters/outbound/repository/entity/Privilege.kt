package ao.path2.app.adapters.outbound.repository.entity

import javax.persistence.*

@Entity
@Table(name = "TB_Privilege")
data class Privilege(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long,
  var name: String,
  @ManyToMany(mappedBy = "privileges")
  var  roles: Collection<Role>
)