package ed.uopp.uoppcore.service.impl;

import ed.uopp.uoppcore.entity.Subscription;
import ed.uopp.uoppcore.repository.SubscriptionRepository;
import ed.uopp.uoppcore.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultSubscriptionService implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription save(Subscription subscription) {
        if (subscriptionRepository.existsByUserId(subscription.getUserId())) {
            throw new IllegalArgumentException("This user already have subscription. Remove existing to create a new one!");
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

}
