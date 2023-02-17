package ao.path2.ms.auth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*
import javax.validation.constraints.*

@Entity(name = "User")
@Table(name = "TB_USER")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = ["password"])
class User() {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_user")
  var id: Long = 0

  @NotBlank
  @NotEmpty
  @NotNull
  @Column(nullable = false)
  var name: String = ""

  @NotBlank
  @NotEmpty
  @NotNull
  @Column(nullable = false, unique = true)
  var username: String = ""

  @NotNull
  @NotEmpty
  @NotBlank
  @Size(min = 9, max = 9)
  @Column(nullable = false, unique = true)
  var phone: String = ""

  @NotNull
  var image: String = ""

  @NotBlank
  @NotEmpty
  @NotNull
  @Column(nullable = false, unique = true)
  var email: String = ""

  @Column(nullable = true, unique = true)
  var facebookId: String? = null

  @Enumerated(EnumType.STRING)
  var createdBy: UserSource = UserSource.EMAIL

  @Size(min = 8)
  @NotNull
  @NotEmpty
  @NotBlank
  @Column(nullable = false)
  var password: String = ""

  var verified: Boolean = false
  var cancelled: Boolean = false
}