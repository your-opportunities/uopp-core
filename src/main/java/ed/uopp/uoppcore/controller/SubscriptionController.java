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
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController implements SubscriptionOperations {

    private final SubscriptionService subscriptionService;
    private final CategoryService categoryService;
    private final FormatService formatService;

    @PostMapping
    @Override
    public ResponseEntity<UUID> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.ok(subscriptionService.save(toSubscription(subscriptionDTO)));
    }

    @GetMapping("/{uuid}")
    @Override
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable UUID uuid) {
        return ResponseEntity.ok(toSubscriptionDto(subscriptionService.getByUuid(uuid)));
    }

    @DeleteMapping("/{uuid}")
    @Override
    public ResponseEntity<Void> deleteSubscription(@PathVariable UUID uuid) {
        subscriptionService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }

    // extract this logic
    private SubscriptionDTO toSubscriptionDto(Subscription subscription) {
        return new SubscriptionDTO(
                subscription.getSubscriptionChannel().toString(),
                subscription.getUserId(),
                subscription.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toSet()),
                subscription.getFormats().stream()
                        .map(Format::getName)
                        .collect(Collectors.toSet()),
                subscription.getIsAsap()
        );
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
