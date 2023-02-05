package ao.path2.core.models

import javax.persistence.*

@Entity
@Table
class Party {
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_party")
  @Id
  var id: Long = 0L
  val name: String = ""
  val email: String = ""
}
