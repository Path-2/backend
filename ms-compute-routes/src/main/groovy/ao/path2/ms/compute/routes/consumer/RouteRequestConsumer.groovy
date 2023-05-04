package ao.path2.ms.compute.routes.consumer

import ao.path2.ms.compute.routes.models.RouteRequest
import ao.path2.ms.compute.routes.service.BestRoute
import com.graphhopper.util.exceptions.PointNotFoundException
import com.rabbitmq.client.Channel
import org.apache.logging.log4j.LogManager
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class RouteRequestConsumer {
    private def log = LogManager.getLogger(RouteRequestConsumer)
    private BestRoute bestRoute

    @Value("spring.rabbitmq.queue.name")
    private String queueName

    RouteRequestConsumer(BestRoute bestRoute) {
        this.bestRoute = bestRoute
    }

    @RabbitListener(queues = ["route.new.request"], id = "listener")
    def computeNewRoute(@Payload RouteRequest route,
                        Channel channel,
                        Message message) {
    try {


        def route1 = bestRoute.compute(route.start, route.end)

        println route1
    } catch(PointNotFoundException ignored) {}
        finally {
            channel.basicAck(message.messageProperties.deliveryTag, true)
        }

    }
}
