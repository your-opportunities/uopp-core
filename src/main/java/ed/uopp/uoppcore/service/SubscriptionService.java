package ed.uopp.uoppcore.service;

import ed.uopp.uoppcore.entity.Subscription;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    UUID save(Subscription subscription);

    List<Subscription> getAll();

    Subscription getByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
