package ao.path2.core.models

import javax.persistence.*

@Entity
@Table(name = "TB_ROUTE")
class Route() {
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_route")
  @Id
  var id: Long = 0L

  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.REFRESH])
  var start: Stop? = null

  @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.REFRESH])
  var end: Stop? = null

  @JoinTable(
    joinColumns = [JoinColumn(name = "route_id", referencedColumnName = "id", unique = true)],
    inverseJoinColumns = [JoinColumn(name = "point_id", referencedColumnName = "id", unique = true)],
    name = "paths"
  )
  @ManyToMany
  var points: List<Point> = listOf()
}