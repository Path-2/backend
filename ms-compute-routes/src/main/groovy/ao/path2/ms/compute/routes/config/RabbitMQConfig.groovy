package ao.path2.ms.compute.routes.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ErrorHandler

@EnableRabbit
@Configuration
class RabbitMQConfig {
    private final queueName = "route.new.request"
    private final exchange = "amqp.direct"
    private final routingKey = ""
    private final replyTimeout = 2
    private final concurrentConsumers = 5
    private final maxConcurrentConsumers = 20

    @Bean
    def DirectExchange exchange() {
        return new DirectExchange(exchange)
    }

    @Bean
    org.springframework.amqp.core.Queue queue() {
        return new org.springframework.amqp.core.Queue(queueName, false)
    }

    @Bean
    org.springframework.amqp.core.Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey)
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper())
    }

    @Bean
    AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final rabbitTemplate = new RabbitTemplate(connectionFactory)
        rabbitTemplate.setDefaultReceiveQueue(queueName)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setReplyAddress(queueName)
        rabbitTemplate.setReplyTimeout(replyTimeout.toLong())
        rabbitTemplate.setUseDirectReplyToContainer(false)
        return rabbitTemplate
    }

    @Bean
    AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory)
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory)  {
        final factory = new SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(jsonMessageConverter())
        factory.setConcurrentConsumers(concurrentConsumers)
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers)
        factory.setErrorHandler(errorHandler())
        return factory
    }

    @Bean
    ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy())
    }

    class MyFatalExceptionStrategy extends
            ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
        @Override
        boolean isFatal(Throwable t) {
            if (t instanceof ListenerExecutionFailedException) {
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
