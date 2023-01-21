package ao.path2.core.models

class Route() {
    lateinit var start: Stop
    lateinit var end: Stop
    lateinit var points: List<Point>
}