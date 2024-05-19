package ed.uopp.uoppcore.controller;

import ed.uopp.uoppcore.data.SubscriptionData;
import ed.uopp.uoppcore.entity.Subscription;
import ed.uopp.uoppcore.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscriptions")
    public Subscription createSubscription(@RequestBody SubscriptionData subscription) {
        return subscriptionService.save(subscription);
    }

}