package ed.uopp.uoppcore.job;

import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.entity.Subscription;
import ed.uopp.uoppcore.service.NotificationService;
import ed.uopp.uoppcore.service.OpportunityService;
import ed.uopp.uoppcore.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(
        value="application.jobs.subscriptionJobEnabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class SubscriptionJob {

    private final SubscriptionService subscriptionService;
    private final OpportunityService opportunityService;
    private final NotificationService notificationService;

    @Scheduled(fixedRate = 10000) // todo: should be updated, only for testing
    public void checkSubscriptions() {
        log.info("Job started");
        List<Subscription> subscriptions = subscriptionService.getAll();
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusHours(1L); // todo: should be updated, only for testing
        List<Opportunity> opportunities = opportunityService.getOpportunitiesCreatedAfter(fiveMinutesAgo);


        for (Subscription subscription : subscriptions) {
            for (Opportunity opportunity : opportunities) {

                boolean hasMatchingCategory = Collections.disjoint(opportunity.getCategories(), subscription.getCategories());
                boolean hasMatchingFormat = Collections.disjoint(opportunity.getFormats(), subscription.getFormats());

                if ((hasMatchingCategory && hasMatchingFormat && opportunity.getIsAsap().equals(subscription.getIsAsap()))) {
                    // todo: do not notify user two times
                    notifyUser(subscription, opportunity);
                }
            }
        }

        log.info("Job finished");
    }

    private void notifyUser(Subscription subscription, Opportunity opportunity) {
        log.info("USER WAS NOTIFIED");
        notificationService.notifyOnOpportunityCreation();
    }
}