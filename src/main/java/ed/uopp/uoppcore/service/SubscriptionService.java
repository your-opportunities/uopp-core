package ed.uopp.uoppcore.service;

import ed.uopp.uoppcore.data.SubscriptionData;
import ed.uopp.uoppcore.entity.Subscription;

import java.util.List;

public interface SubscriptionService {

    Subscription save(SubscriptionData subscription);

    List<Subscription> getAll();

}
