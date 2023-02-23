package ao.path2.ms.stop.models

import javax.persistence.*

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