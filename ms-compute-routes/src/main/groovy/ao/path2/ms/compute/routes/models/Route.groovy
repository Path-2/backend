package ao.path2.ms.compute.routes.models

class Route {

    private Route() {
        nodes = new ArrayList<>()
    }

    private Point start
    private Point end
    private List<Point> nodes
    private double distanceInMeters
    private long timeInMS

    static def from(List<Point> points) {
        def route = new Route()

        for (i in 0..<points.size()) {
            if (i == 0)
                route.start = points[i]
            else if (i == points.size() - 1)
                route.end = points[i]
            else route.nodes += points[i]
        }

        return route
    }

    void setDistanceInMeters(double distance) {
        this.distanceInMeters = distance
    }

    void setTimeInMS(long timeInMS) {
        this.timeInMS = timeInMS
    }

    List<Point> to() {
        return start + nodes + end
    }

    @Override
    String toString() {
        return "Route{start=${start}, end=${end}, nodes=${nodes}}"
    }
}
