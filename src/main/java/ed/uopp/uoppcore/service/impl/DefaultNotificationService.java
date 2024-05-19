package ed.uopp.uoppcore.service.impl;

import ed.uopp.uoppcore.messaging.producer.NotificationProducer;
import ed.uopp.uoppcore.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultNotificationService implements NotificationService {

    private final NotificationProducer notificationProducer;

    @Override
    public void notifyOnOpportunityCreation() {
        log.info("Sending notifications to mq");

        notificationProducer.sendMessage("SOMETHING for telegram " + UUID.randomUUID(), "telegram-key");
        notificationProducer.sendMessage("SOMETHING for email " + UUID.randomUUID(), "email-key");

        log.info("Sent notifications to mq");
    }

}
