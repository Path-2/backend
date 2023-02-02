package ao.path2.ms.email.consumer

import ao.path2.ms.email.service.EmailService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service


@Service
class RabbitMQConsumer(private val emailSender: EmailService) {
  private val logger: Logger = LogManager.getLogger(RabbitMQConsumer::class.java.toString())

  @RabbitListener(queues = ["user.name"], id = "listener")
  fun receiverVerifyEmail(@Payload user: Any) {
    logger.info("Listener invoked - Consuming Message with MenuOrder Identifier : $user")

    val data: MutableMap<String, Any> = HashMap()

    data["name"] = "Dumildes Paulo"

    emailSender.sendAttachedWithAssetsEmail(
      data,
      "Verificação de email",
      "dumi703@gmail.com;",
      "verify-email")
  }

  @RabbitListener(queues = ["user.name"], id = "listener")
  fun receiverNews(@Payload user: Any) {
    logger.info("Listener invoked - Consuming Message with MenuOrder Identifier : $user")

    val data: MutableMap<String, Any> = HashMap()

    data["name"] = "Dumildes Paulo"

    emailSender.sendAttachedWithAssetsEmail(
      data,
      "Novidades ${data["name"]}",
      "dumi703@gmail.com;",
      "news")
  }

  @RabbitListener(queues = ["user.name"], id = "listener")
  fun receiverGreetings(@Payload user: Any) {
    logger.info("Listener invoked - Consuming Message with MenuOrder Identifier : $user")

    val data: MutableMap<String, Any> = HashMap()

    data["name"] = "Dumildes Paulo"

    emailSender.sendAttachedWithAssetsEmail(
      data,
      "Seja bem-vindo",
      "dumi703@gmail.com;",
      "greetings")
  }
}