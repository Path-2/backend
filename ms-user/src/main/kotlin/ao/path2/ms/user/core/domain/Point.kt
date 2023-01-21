package ao.path2.ms.user.core.domain

import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

open class Point(var coordinates: Point, createdAt: LocalDateTime, createdBy: User) {}
