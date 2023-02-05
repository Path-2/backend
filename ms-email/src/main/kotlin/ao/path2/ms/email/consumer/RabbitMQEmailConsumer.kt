package ao.path2.ms.email.consumer

import ao.path2.core.models.EmailModel
import ao.path2.ms.email.producer.RabbiMQEmailProducer
import ao.path2.ms.email.service.EmailService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import java.util.*

@Service
class RabbitMQEmailConsumer(private val emailSender: EmailService, private val producer: RabbiMQEmailProducer) {
  private val log: Logger = LogManager.getLogger(RabbitMQEmailConsumer::class.java.toString())

  @RabbitListener(queues = ["user.name"], id = "listener")
  fun receiverVerifyEmail(@Payload model: EmailModel) {
    log.info("Listener invoked - Consuming Message with EmailModel Identifier : ${model.id}")

    var messageValid = true

    try {

      if (model.subject == ""){
        log.warn("Subject is empty")
        messageValid = false
      }

      if (model.data.isEmpty()) {
        log.warn("Data is empty...")
      }

      if (model.template == "") {
        log.warn("Template is mandatory, please choose one...")
        messageValid = false
      }

      if (model.to.isEmpty()) {
        log.warn("Have some errors into message, please fix it...")
        messageValid = false
      }

      if (!messageValid) return

      log.info("Sending email to ${model.to.contentToString()}")
      emailSender.sendAttachedWithAssetsEmail(model)

      log.info("Email sent with success - Message with EmailModel Identifier : ${model.id}")
    } catch (ex: Exception) {
      log.error("Sent email failed...")
      tryingEnQueue(model, 0)
    }
  }

  fun tryingEnQueue(model: EmailModel, times: Int) {
    try {
      log.error("Enqueue failed, trying again...$times")
      producer.enqueue(model)
    } catch (ex2: Exception) {
      tryingEnQueue(model, times + 1)
      log.info("Message enqueued with success...")
    }
  }
}