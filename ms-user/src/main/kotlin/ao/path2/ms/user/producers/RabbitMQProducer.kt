package ao.path2.ms.user.producers

import ao.path2.core.models.EmailModel
import org.springframework.amqp.AmqpException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class RabbitMQProducer(private val rabbitTemplate: RabbitTemplate) {

  @Throws(AmqpException::class)
  fun enqueue(emailModel: EmailModel) {
    rabbitTemplate.convertAndSend("user.name", emailModel)
  }

}