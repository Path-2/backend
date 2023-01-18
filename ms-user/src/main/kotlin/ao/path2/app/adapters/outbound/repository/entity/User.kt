package ao.path2.app.adapters.outbound.repository.entity

import ao.path2.app.core.domain.Role
import javax.persistence.*
import javax.validation.constraints.*

@Entity(name = "User")
@Table(name = "TB_USER")
class User {
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
  @Email
  @Column(nullable = false, unique = true)
  var email: String = ""

  @Size(min = 8)
  @NotNull
  @NotEmpty
  @NotBlank
  @Column(nullable = false)
  var password: String = ""

  @ManyToMany
  @JoinTable(
    name = "users_roles",
    joinColumns = [JoinColumn(
      name = "user_id", referencedColumnName = "id"
    )],
    inverseJoinColumns = [JoinColumn(
      name = "role_id", referencedColumnName = "id"
    )]
  )
  var roles: List<Role> = listOf()
}