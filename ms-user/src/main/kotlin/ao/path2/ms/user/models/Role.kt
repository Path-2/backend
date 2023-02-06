package ao.path2.ms.user.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TB_ROLE")
class Role() {
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_role")
  @Id
  var id: Long = 0L
  var name: String = ""

  constructor(name: String) : this() {
    this.name = name
  }
}