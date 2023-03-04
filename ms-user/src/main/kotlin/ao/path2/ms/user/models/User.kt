package ao.path2.ms.user.models

import ao.path2.ms.user.models.enums.UserSource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
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

  var image: String = ""

  @Column
  var email: String? = null

  @Column(nullable = true)
  var facebookId: String? = null

  @Enumerated(EnumType.STRING)
  var createdBy: UserSource = UserSource.EMAIL

  @Size(min = 8)
  @NotNull
  @NotEmpty
  @NotBlank
  @Column(nullable = false)
  var password: String? = ""

  @Column(nullable = false)
  var verified: Boolean = false

  @Column(nullable = false)
  var cancelled: Boolean = false

  @Column(nullable = false)
  var createdAt: LocalDateTime = LocalDateTime.now()

  @Column(nullable = false)
  var updatedAt: LocalDateTime = LocalDateTime.now()

  @Column(nullable = false)
  var passwordUpdatedAt: LocalDateTime = LocalDateTime.now()

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
  override fun toString(): String {
    return "User(id=$id, name='$name', username='$username', phone='$phone', image='$image', email='$email', facebookId=$facebookId, createdBy=$createdBy, password=$password, verified=$verified, cancelled=$cancelled, roles=$roles)"
  }
}