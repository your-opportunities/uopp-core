package ed.uopp.uoppcore.messaging.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class NotificationProducer {

    private final AmqpTemplate amqpTemplate;

    @Value("${application.rabbitmq.exchange}")
    private String exchange;

    public void sendMessage(String message, String routingKey) {
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }
}