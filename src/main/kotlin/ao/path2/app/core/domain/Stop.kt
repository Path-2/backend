package ao.path2.app.core.domain

import java.time.LocalDateTime

class Stop(name: String, lat: Double, lon: Double, createdAt: LocalDateTime, createdBy: User) : Point(
    name, lat, lon, createdAt,
    createdBy
) {}