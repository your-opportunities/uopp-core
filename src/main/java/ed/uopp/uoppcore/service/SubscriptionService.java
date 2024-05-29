package ed.uopp.uoppcore.service;

import ed.uopp.uoppcore.data.http.SubscriptionDTO;
import ed.uopp.uoppcore.entity.Subscription;

import java.util.List;

public interface SubscriptionService {

    Subscription save(Subscription subscription);

    List<Subscription> getAll();

}
