package ao.path2.ms.compute.routes.consumer

import ao.path2.ms.compute.routes.models.RouteRequest
import ao.path2.ms.compute.routes.service.BestRoute
import org.apache.logging.log4j.LogManager
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class RouteRequestConsumer {
    private def log = LogManager.getLogger(RouteRequestConsumer)
    private BestRoute bestRoute

    RouteRequestConsumer(BestRoute bestRoute) {
        this.bestRoute = bestRoute
    }

    @RabbitListener(queues = ["route.new.request"], id = "listener")
    def computeNewRoute(@Payload RouteRequest route) {

        def route1 = bestRoute.compute(route.start, route.end)

        println route1

    }
}
