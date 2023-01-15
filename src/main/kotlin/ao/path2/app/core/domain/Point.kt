package ao.path2.app.core.domain

import java.time.LocalDateTime

open class Point(var name: String, var lat: Double, var lon: Double, createdAt: LocalDateTime, createdBy: User) {}
