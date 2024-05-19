package ed.uopp.uoppcore.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationProducerConfig {

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Value("${application.rabbitmq.exchange}")
    private String exchange;
    @Value("${application.rabbitmq.routing-key.email}")
    private String routingKeyEmail;
    @Value("${application.rabbitmq.routing-key.telegram}")
    private String routingKeyTelegram;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue queueEmail() {
        return new Queue("email.queue", true);
    }

    @Bean
    Queue queueTelegram() {
        return new Queue("telegram.queue", true);
    }

    @Bean
    Binding bindingEmail(Queue queueEmail, DirectExchange exchange) {
        return BindingBuilder.bind(queueEmail).to(exchange).with(routingKeyEmail);
    }

    @Bean
    Binding bindingTelegram(Queue queueTelegram, DirectExchange exchange) {
        return BindingBuilder.bind(queueTelegram).to(exchange).with(routingKeyTelegram);
    }

}
