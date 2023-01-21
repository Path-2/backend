package ao.path2.ms.user.core.domain

class Route() {
    lateinit var start: Stop
    lateinit var end: Stop
    lateinit var points: List<Point>
}