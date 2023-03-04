package ao.path2.ms.forgot.producers

import ao.path2.ms.forgot.models.EmailModel
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbitMQProducer(private val rabbitTemplate: RabbitTemplate) {

  fun enqueue(emailModel: EmailModel) {
    try {
      rabbitTemplate.convertAndSend("user.forgot", emailModel)
    } catch (_: Exception) {
      enqueue(emailModel)
    }
  }

}