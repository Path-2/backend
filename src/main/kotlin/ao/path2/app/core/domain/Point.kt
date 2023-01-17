package ao.path2.app.core.domain

import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

open class Point(var coordinates: Point, createdAt: LocalDateTime, createdBy: User) {}
