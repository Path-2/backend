package ao.path2.app.adapters.outbound.repository.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "TB_ROLE")
class Role {
  @GeneratedValue
  @Id
  val id: Long = 0

  @NotNull
  var name: String = ""
}
