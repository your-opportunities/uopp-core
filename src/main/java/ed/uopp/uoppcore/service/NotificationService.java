package ed.uopp.uoppcore.service;

import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.entity.SubscriptionChannel;

import java.util.UUID;

public interface NotificationService {

    void notifyOnOpportunity(SubscriptionChannel subscriptionChannel, UUID uuid, String userId, Opportunity opportunity);

}
