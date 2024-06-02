package ed.uopp.uoppcore.messaging.consumer;

import ed.uopp.uoppcore.data.mq.FullMessageDTO;
import ed.uopp.uoppcore.messaging.processor.OpportunityMessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OpportunityMessageConsumer {

    private final OpportunityMessageProcessor opportunityMessageProcessor;

    @Retryable(retryFor = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    @RabbitListener(queues = "${application.rabbitmq.processed-queue}")
    public void consume(FullMessageDTO message) {
        log.info("Received message: {}", message);

        opportunityMessageProcessor.processOpportunityMessage(message);
    }

    @Recover
    public void recover(Exception ex) {
        log.error("Failed to process");
    }

}
