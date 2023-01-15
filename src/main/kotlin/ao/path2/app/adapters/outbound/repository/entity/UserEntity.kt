package ao.path2.app.adapters.outbound.repository.entity

import javax.persistence.*
import javax.validation.constraints.*

@Entity(name = "User")
@Table(name = "TB_USER")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_user")
    var id: Long? = null
    
    @NotBlank
    @NotEmpty
    @NotNull
    @Column(nullable = false, unique = true)
    lateinit var name: String

    @NotBlank
    @NotEmpty
    @NotNull
    @Column(nullable = false, unique = true)
    lateinit var username: String
    
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 9, max = 9)
    @Column(nullable = false, unique = true)
    lateinit var phone: String

    @NotBlank
    @NotNull
    lateinit var image: String

    @NotBlank
    @NotEmpty
    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    lateinit var email: String
    
    @Size(min = 8)
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(nullable = false, unique = true)
    lateinit var password: String
}