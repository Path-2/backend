package ao.path2.app.adapters.outbound.repository.entity

import ao.path2.app.utils.annotations.GlobalValidator
import javax.persistence.*

@Entity(name = "User")
@Table(name = "TB_USER")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_user")
    var id: Long? = null
    @GlobalValidator
    @Column(nullable = false, unique = true)
    lateinit var name: String
    @GlobalValidator
    @Column(nullable = false, unique = true)
    lateinit var phone: String
    @GlobalValidator
    @Column(nullable = false, unique = true)
    lateinit var image: String
    @GlobalValidator
    @Column(nullable = false, unique = true)
    lateinit var email: String
    @GlobalValidator
    @Column(nullable = false, unique = true)
    lateinit var password: String
}