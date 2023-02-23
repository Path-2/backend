package ao.path2.ms.email.producer

import ao.path2.core.models.EmailModel
import org.springframework.amqp.AmqpException
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbiMQEmailProducer(private val rabbitTemplate: RabbitTemplate, private val queue: Queue) {

  @Throws(AmqpException::class)
  fun enqueue(emailModel: EmailModel) {
    rabbitTemplate.convertAndSend(queue.name, emailModel)
  }
}