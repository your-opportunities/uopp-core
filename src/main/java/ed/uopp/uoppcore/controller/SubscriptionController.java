package ed.uopp.uoppcore.controller;

import ed.uopp.uoppcore.controller.operations.SubscriptionOperations;
import ed.uopp.uoppcore.data.http.SubscriptionDTO;
import ed.uopp.uoppcore.entity.Category;
import ed.uopp.uoppcore.entity.Format;
import ed.uopp.uoppcore.entity.Subscription;
import ed.uopp.uoppcore.entity.SubscriptionChannel;
import ed.uopp.uoppcore.service.CategoryService;
import ed.uopp.uoppcore.service.FormatService;
import ed.uopp.uoppcore.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController implements SubscriptionOperations {

    private final SubscriptionService subscriptionService;
    private final CategoryService categoryService;
    private final FormatService formatService;


    @PostMapping
    @Override
    public ResponseEntity<Void> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        subscriptionService.save(toSubscription(subscriptionDTO));
        return ResponseEntity.ok().build();
    }


    // extract this logic
    public Subscription toSubscription(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = new Subscription();
        subscription.setUserId(subscriptionDTO.userId());
        subscription.setSubscriptionChannel(SubscriptionChannel.valueOf(subscriptionDTO.subscriptionChannel()));
        subscription.setIsAsap(subscriptionDTO.isAsap());

        Set<Category> categories = new HashSet<>();
        subscriptionDTO.categories().forEach(categoryName -> {
            Category category = categoryService.getCategoryByName(categoryName);
            categories.add(category);
        });
        subscription.setCategories(categories);

        Set<Format> formats = new HashSet<>();
        subscriptionDTO.formats().forEach(formatName -> {
            Format format = formatService.getFormatByName(formatName);
            formats.add(format);
        });
        subscription.setFormats(formats);
        subscription.setUuid(UUID.randomUUID());

        return subscription;
    }

}
