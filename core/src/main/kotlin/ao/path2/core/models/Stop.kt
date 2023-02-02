package ao.path2.core.models

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Stop(): Point() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_points")
    var id: Long? = null
    var createdAt: LocalDateTime = LocalDateTime.now()
    @ManyToOne
    var createdBy: User? = null
}