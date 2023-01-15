package ao.path2.app.core.domain

class Route() {
    lateinit var start: Stop
    lateinit var end: Stop
    lateinit var points: List<Point>
}