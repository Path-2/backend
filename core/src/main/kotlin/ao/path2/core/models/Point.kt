package ao.path2.core.models

import javax.persistence.Embeddable
import javax.persistence.MappedSuperclass

@MappedSuperclass
@Embeddable
open class Point {
  var name: String = ""
  var x: Double = .0
  var y: Double = .0
}