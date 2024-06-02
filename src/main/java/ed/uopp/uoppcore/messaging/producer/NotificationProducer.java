package ed.uopp.uoppcore.messaging.producer;

import ed.uopp.uoppcore.data.mq.NotificationDTO;
import ed.uopp.uoppcore.entity.SubscriptionChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationProducer {

    @Value("${application.rabbitmq.exchange}")
    private String exchange;
    @Value("${application.rabbitmq.routing-key.email}")
    private String routingKeyEmail;
    @Value("${application.rabbitmq.routing-key.telegram}")
    private String routingKeyTelegram;

    private final AmqpTemplate amqpTemplate;

    public void sendMessage(NotificationDTO notificationDTO, SubscriptionChannel subscriptionChannel) {
        String routingKey = defineRoutingKey(subscriptionChannel);
        amqpTemplate.convertAndSend(exchange, routingKey, notificationDTO);
        log.info("NotificationDTO was sent to user with userId = '{}'", notificationDTO.userId());
    }

    private String defineRoutingKey(SubscriptionChannel subscriptionChannel) {
        return switch (subscriptionChannel) {
            case EMAIL -> routingKeyEmail;
            case TELEGRAM -> routingKeyTelegram;
        };
    }

}