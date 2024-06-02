package ed.uopp.uoppcore.job;

import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.entity.Subscription;
import ed.uopp.uoppcore.service.NotificationService;
import ed.uopp.uoppcore.service.OpportunityService;
import ed.uopp.uoppcore.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(
        value = "application.jobs.subscriptionJob.enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class SubscriptionJob {

    @Value("${application.jobs.subscriptionJob.intervalInSeconds}")
    private Long intervalInSeconds;
    private final SubscriptionService subscriptionService;
    private final OpportunityService opportunityService;
    private final NotificationService notificationService;

    @Scheduled(fixedRateString = "#{${application.jobs.subscriptionJob.intervalInSeconds} * 1000}")
    public void checkSubscriptions() {
        log.info("Job started");
        List<Subscription> subscriptions = subscriptionService.getAll();
        LocalDateTime dateTimeThreshold = LocalDateTime.now().minusSeconds(intervalInSeconds);
        List<Opportunity> opportunities = opportunityService.getOpportunitiesUpdatedAfter(dateTimeThreshold);

        for (Subscription subscription : subscriptions) {
            for (Opportunity opportunity : opportunities) {

                boolean hasMatchingCategory = Collections.disjoint(opportunity.getCategories(), subscription.getCategories());
                boolean hasMatchingFormat = Collections.disjoint(opportunity.getFormats(), subscription.getFormats());

                if ((hasMatchingCategory && hasMatchingFormat && opportunity.getIsAsap().equals(subscription.getIsAsap()))) {
                    notifyUser(subscription, opportunity);
                }
            }
        }

        log.info("Job finished");
    }

    private void notifyUser(Subscription subscription, Opportunity opportunity) {
        log.info("User userId = '{}' was notified though channel = '{}' with opportunity uuid = '{}'",
                subscription.getUserId(), subscription.getSubscriptionChannel(), opportunity.getUuid());
        notificationService.notifyOnOpportunity(subscription.getSubscriptionChannel(), subscription.getUuid(), subscription.getUserId(), opportunity);
    }
}
