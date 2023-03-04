package ao.path2.ms.forgot.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.validator.constraints.URL
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.*

@Entity(name = "User")
@Table(name = "TB_USER")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = ["password"])
class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_user")
  var id: Long? = null

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

  @Column
  var phone: String? = null

  @Column
  var email: String? = null

  @Size(min = 8)
  @NotNull
  @NotEmpty
  @NotBlank
  @Column(nullable = false)
  var password: String? = ""

  //@Column(nullable = false)
  var updatedAt: LocalDateTime = LocalDateTime.now()

  //@Column(nullable = false)
  var passwordUpdatedAt: LocalDateTime? = null

  @ManyToMany
  @JoinTable(
    name = "tb_permissions",
    joinColumns = [JoinColumn(
      name = "user_id", referencedColumnName = "id"
    )],
    inverseJoinColumns = [JoinColumn(
      name = "role_id", referencedColumnName = "id"
    )]
  )
  var roles: List<Role>? = listOf()

}