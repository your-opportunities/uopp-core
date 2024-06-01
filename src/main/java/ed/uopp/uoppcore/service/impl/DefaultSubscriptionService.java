package ed.uopp.uoppcore.service.impl;

import ed.uopp.uoppcore.entity.Subscription;
import ed.uopp.uoppcore.exception.DuplicateException;
import ed.uopp.uoppcore.repository.SubscriptionRepository;
import ed.uopp.uoppcore.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DefaultSubscriptionService implements SubscriptionService {

    private static final String NO_SUBSCRIPTION_WITH_UUID_FOUND = "No subscription with UUID '%s' found";
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public UUID save(Subscription subscription) {
        String userId = subscription.getUserId();
        if (subscriptionRepository.existsByUserId(userId)) {
            throw new DuplicateException(String.format("The user %s already have subscription. Remove existing to create a new one!",
                    userId));
        }
        subscriptionRepository.save(subscription);
        return subscription.getUuid();
    }

    @Override
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription getByUuid(UUID uuid) {
        return subscriptionRepository.findByUuid(uuid)
                .orElseThrow(() -> new NoSuchElementException(String.format(NO_SUBSCRIPTION_WITH_UUID_FOUND, uuid)));
    }

    @Override
    @Transactional // todo: review
    public void deleteByUuid(UUID uuid) {
        if (!subscriptionRepository.existsByUuid(uuid)) {
            throw new IllegalArgumentException(String.format("Subscription with uuid '%s' does not exist, thus can not be deleted!", uuid));
        }
        subscriptionRepository.deleteByUuid(uuid);
    }

}
