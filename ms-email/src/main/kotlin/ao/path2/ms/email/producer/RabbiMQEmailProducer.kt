package ao.path2.ms.email.producer

import ao.path2.ms.email.models.EmailModel
import org.springframework.amqp.AmqpException
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbiMQEmailProducer(private val rabbitTemplate: RabbitTemplate) {

  @Throws(AmqpException::class)
  fun enqueue(queue: String, emailModel: EmailModel) {
    rabbitTemplate.convertAndSend(queue, emailModel)
  }
}