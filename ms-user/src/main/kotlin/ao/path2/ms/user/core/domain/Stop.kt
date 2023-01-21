package ao.path2.ms.user.core.domain

import java.time.LocalDateTime

class Stop(coordinates: org.locationtech.jts.geom.Point, createdAt: LocalDateTime, createdBy: User, ) : Point(coordinates, createdAt,
    createdBy
)