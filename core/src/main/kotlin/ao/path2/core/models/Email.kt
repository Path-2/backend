package ao.path2.core.models

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "TB_EMAIL")
class Email {
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_email")
  @Id
  var id : Long = 0L
  @ManyToOne
  var from: Party? = null
  @ManyToOne
  var to: Party? = null
  var message: String = ""
  var timestamp: LocalDateTime = LocalDateTime.now()
}