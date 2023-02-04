package ao.path2.ms.email.consumer

import ao.path2.core.models.EmailModel
import ao.path2.ms.email.service.EmailService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class RabbitMQEmailConsumer(private val emailSender: EmailService) {
  private val log: Logger = LogManager.getLogger(RabbitMQEmailConsumer::class.java.toString())

  @RabbitListener(queues = ["user.name"], id = "listener")
  fun receiverVerifyEmail(@Payload model: EmailModel) {
    log.info("Listener invoked - Consuming Message with EmailModel Identifier : ${model.id}")

    try {
      emailSender.sendAttachedWithAssetsEmail(model)

      log.info("Email sent with success - Message with EmailModel Identifier : ${model.id}")
    } catch (ex: Exception) {
      log.error("Sent email failed...")
    }
  }
}