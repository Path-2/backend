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
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ErrorHandler


@EnableRabbit
@Configuration
class RabbitMQConfig() {
  @Value("\${rabbitmq.queue}")
  private val queueName: String? = null

  @Value("\${rabbitmq.exchange}")
  private val exchange: String? = null

  @Value("\${rabbitmq.routingkey}")
  private val routingkey: String? = null

  @Value("\${rabbitmq.username}")
  private val username: String? = null

  @Value("\${rabbitmq.password}")
  private val password: String? = null

  @Value("\${rabbitmq.host}")
  private val host: String? = null

  @Value("\${rabbitmq.virtualhost}")
  private val virtualHost: String? = null

  @Value("\${rabbitmq.reply.timeout}")
  private val replyTimeout: Int? = null

  @Value("\${rabbitmq.concurrent.consumers}")
  private val concurrentConsumers: Int? = null

  @Value("\${rabbitmq.max.concurrent.consumers}")
  private val maxConcurrentConsumers: Int? = null
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
    return BindingBuilder.bind(queue).to(exchange).with(routingkey)
  }

  @Bean
  fun jsonMessageConverter(): MessageConverter {
    return Jackson2JsonMessageConverter(ObjectMapper())
  }

  @Bean
  fun connectionFactory(): ConnectionFactory {
    val connectionFactory = CachingConnectionFactory()
    connectionFactory.virtualHost = (virtualHost)!!
    connectionFactory.host = (host)!!
    connectionFactory.username = (username)!!
    connectionFactory.setPassword(password!!)
    return connectionFactory
  }

  @Bean
  fun rabbitTemplate(connectionFactory: ConnectionFactory?): AmqpTemplate {
    val rabbitTemplate = RabbitTemplate(connectionFactory!!)
    rabbitTemplate.setDefaultReceiveQueue(queueName!!)
    rabbitTemplate.messageConverter = jsonMessageConverter()
    rabbitTemplate.setReplyAddress(queue().name)
    rabbitTemplate.setReplyTimeout(replyTimeout!!.toLong())
    rabbitTemplate.setUseDirectReplyToContainer(false)
    return rabbitTemplate
  }

  @Bean
  fun amqpAdmin(): AmqpAdmin {
    return RabbitAdmin(connectionFactory())
  }

  @Bean
  fun rabbitListenerContainerFactory(): SimpleRabbitListenerContainerFactory {
    val factory = SimpleRabbitListenerContainerFactory()
    factory.setConnectionFactory(connectionFactory())
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
        val lefe = t
        logger.error(
          "Failed to process inbound message from queue "
              + lefe.failedMessage.messageProperties.consumerQueue
              + "; failed message: " + lefe.failedMessage, t
        )
      }
      return super.isFatal(t)
    }
  }
}