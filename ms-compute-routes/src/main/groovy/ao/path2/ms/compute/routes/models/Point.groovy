package ao.path2.ms.compute.routes.models

class Point {
    private Point() {}

    double lat
    double lon

    static def from(double lat, double lon) {
        def point = new Point()

        point.lat = lat
        point.lon = lon

        return point
    }

    @Override
    String toString() {
        return "Point{lat=${lat}, lon=${lon}}"
    }
}
