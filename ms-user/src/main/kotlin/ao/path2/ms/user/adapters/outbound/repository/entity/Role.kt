package ao.path2.ms.user.adapters.outbound.repository.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
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
