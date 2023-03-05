package ao.path2.ms.compute.routes.service

import ao.path2.ms.compute.routes.models.Point
import ao.path2.ms.compute.routes.models.Route
import com.graphhopper.GHRequest
import com.graphhopper.GHResponse
import com.graphhopper.GraphHopper
import com.graphhopper.ResponsePath
import com.graphhopper.util.PointList
import com.graphhopper.util.shapes.GHPoint
import org.springframework.stereotype.Service

@Service
class BestRoute {

    private final GraphHopper hopper
    private List<Point> points
    private Route route

    Route getRoute() {
        return route
    }

    BestRoute(GraphHopper hopper) {
        this.hopper = hopper
    }

    def compute(Point start, Point end) {

        // Calcula a rota mais curta entre os pontos
        GHRequest req = new GHRequest(start.lat, start.lon, end.lat, end.lon)
                .setProfile("car")
        // define the language for the turn instructions
                .setLocale(Locale.US)
        GHResponse rsp = hopper.route(req)

        // Verifica se a rota foi encontrada
        if (rsp.hasErrors()) {
            println("Erro ao calcular rota: ${rsp.getErrors()}")
        }

        // use the best path, see the GHResponse class for more possibilities.
        ResponsePath path = rsp.getBest()

        // Imprime os pontos da rota
        PointList points = path.getPoints()

        for (GHPoint point : points) {
            println("${point.lat},${point.lon}")
        }

        route = Route.from(
                points.collect { Point.from(it.lat, it.lon) }
        )

        route.distanceInMeters = path.distance
        route.timeInMS = path.time

        return route
    }
}
