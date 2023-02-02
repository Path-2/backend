package ao.path2.ms.email.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler.DefaultExceptionStrategy
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ErrorHandler


@EnableRabbit
@Configuration
class RabbitMQConfig() {
  private val queueName: String = "user.name"
  private val exchange: String = "amqp.direct"
  private val routingKey: String = ""
  private val replyTimeout: Int = 2
  private val concurrentConsumers: Int = 5
  private val maxConcurrentConsumers: Int = 20

  @Bean
  fun queue(): Queue {
    return Queue(queueName, false)
  }

  @Bean
  fun exchange(): DirectExchange {
    return DirectExchange(exchange)
  }

  @Bean
  fun binding(queue: Queue?, exchange: DirectExchange?): Binding {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey)
  }

  @Bean
  fun jsonMessageConverter(): MessageConverter {
    return Jackson2JsonMessageConverter(ObjectMapper())
  }

  @Bean
  fun rabbitTemplate(connectionFactory: ConnectionFactory): AmqpTemplate {
    val rabbitTemplate = RabbitTemplate(connectionFactory)
    rabbitTemplate.setDefaultReceiveQueue(queueName)
    rabbitTemplate.messageConverter = jsonMessageConverter()
    rabbitTemplate.setReplyAddress(queue().name)
    rabbitTemplate.setReplyTimeout(replyTimeout.toLong())
    rabbitTemplate.setUseDirectReplyToContainer(false)
    return rabbitTemplate
  }

  @Bean
  fun amqpAdmin(connectionFactory: ConnectionFactory): AmqpAdmin {
    return RabbitAdmin(connectionFactory)
  }

  @Bean
  fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory): SimpleRabbitListenerContainerFactory {
    val factory = SimpleRabbitListenerContainerFactory()
    factory.setConnectionFactory(connectionFactory)
    factory.setMessageConverter(jsonMessageConverter())
    factory.setConcurrentConsumers(concurrentConsumers)
    factory.setMaxConcurrentConsumers(maxConcurrentConsumers)
    factory.setErrorHandler(errorHandler())
    return factory
  }

  @Bean
  fun errorHandler(): ErrorHandler {
    return ConditionalRejectingErrorHandler(MyFatalExceptionStrategy())
  }

  class MyFatalExceptionStrategy() : DefaultExceptionStrategy() {
    private val logger = LogManager.getLogger(javaClass)
    override fun isFatal(t: Throwable): Boolean {
      if (t is ListenerExecutionFailedException) {
        logger.error(
          "Failed to process inbound message from queue "
              + t.failedMessage.messageProperties.consumerQueue
              + "; failed message: " + t.failedMessage, t
        )
      }
      return super.isFatal(t)
    }
  }
}