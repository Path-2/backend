package ao.path2.ms.auth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity(name = "User")
@Table(name = "TB_USER")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = ["password"])
class User() {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_user")
  var id: Long = 0

  @Column(nullable = false)
  var name: String = ""

  @Column(nullable = false, unique = true)
  var username: String = ""

  @Column(nullable = false, unique = true)
  var phone: String = ""

  var image: String = ""

  @Column(nullable = false, unique = true)
  var email: String = ""

  @Column(nullable = true, unique = true)
  var facebookId: String? = null

  @Enumerated(EnumType.STRING)
  var createdBy: UserSource = UserSource.EMAIL

  @Column(nullable = false)
  var password: String = ""

  var verified: Boolean = false
  var cancelled: Boolean = false
}