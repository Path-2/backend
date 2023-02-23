package ao.path2.ms.user.producers

import ao.path2.ms.user.models.EmailModel
import org.springframework.amqp.AmqpException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbitMQProducer(private val rabbitTemplate: RabbitTemplate) {

  @Throws(AmqpException::class)
  fun enqueue(emailModel: EmailModel) {
    rabbitTemplate.convertAndSend("user.name", emailModel)
  }

}