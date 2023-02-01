package ao.path2.ms.email.consumer

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component


@Component
@RabbitListener(queues = ["rabbitmq.queue"], id = "listener")
class RabbitMQConsumer {
  private val logger: Logger = LogManager.getLogger(RabbitMQConsumer::class.java.toString())

  @RabbitHandler
  fun receiver(menuOrder: Any) {
    logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : $menuOrder")
  }
}