package ed.uopp.uoppcore.service.impl;

import ed.uopp.uoppcore.data.SubscriptionData;
import ed.uopp.uoppcore.entity.Category;
import ed.uopp.uoppcore.entity.Format;
import ed.uopp.uoppcore.entity.Subscription;
import ed.uopp.uoppcore.entity.SubscriptionChannel;
import ed.uopp.uoppcore.repository.CategoryRepository;
import ed.uopp.uoppcore.repository.FormatRepository;
import ed.uopp.uoppcore.repository.SubscriptionRepository;
import ed.uopp.uoppcore.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultSubscriptionService implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CategoryRepository categoryRepository;
    private final FormatRepository formatRepository;

    @Override
    public Subscription save(SubscriptionData subscriptionData) {
        Subscription subscription = dtoToSubscription(subscriptionData);

        Set<Category> managedCategories = subscriptionData.getCategories().stream()
                .map(category -> categoryRepository.findByName(category)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found: " + category)))
                .collect(Collectors.toSet());
        subscription.setCategories(managedCategories);
        Set<Format> managedFormats = subscriptionData.getFormats().stream()
                .map(format -> formatRepository.findByName(format)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found: " + format)))
                .collect(Collectors.toSet());
        subscription.setFormats(managedFormats);


        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

    public Subscription dtoToSubscription(SubscriptionData dto) {
        Subscription subscription = new Subscription();
        subscription.setUserId(dto.getUserId());
        subscription.setSubscriptionChannel(SubscriptionChannel.valueOf(dto.getSubscriptionChannel()));
        subscription.setIsAsap(dto.getIsAsap());

        return subscription;
    }

}
