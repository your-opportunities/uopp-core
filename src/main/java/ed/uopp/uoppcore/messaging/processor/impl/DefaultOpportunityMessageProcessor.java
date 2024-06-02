package ed.uopp.uoppcore.messaging.processor.impl;

import ed.uopp.uoppcore.data.mq.FullMessageDTO;
import ed.uopp.uoppcore.entity.*;
import ed.uopp.uoppcore.messaging.processor.OpportunityMessageProcessor;
import ed.uopp.uoppcore.security.data.User;
import ed.uopp.uoppcore.security.service.UserService;
import ed.uopp.uoppcore.service.CategoryService;
import ed.uopp.uoppcore.service.FormatService;
import ed.uopp.uoppcore.service.NotificationService;
import ed.uopp.uoppcore.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static ed.uopp.uoppcore.util.DateToLocalDateTimeUtil.getLocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultOpportunityMessageProcessor implements OpportunityMessageProcessor {

    public static final String TELEGRAM_URL = "https://web.telegram.org/a/#"; // todo: move to config
    private final CategoryService categoryService;
    private final FormatService formatService;
    private final OpportunityService opportunityService;
    private final NotificationService notificationService;
    private final UserService userService;

    @Override
    public void processOpportunityMessage(FullMessageDTO fullMessageDTO) {
        Opportunity opportunity = convertToOpportunity(fullMessageDTO);
        Opportunity savedOpportunity = opportunityService.save(opportunity);

        log.info("Processed and saved opportunity. Going to notify moderators");

        List<User> moderators = userService.getAllModerators();
        moderators.forEach(
                moderator -> notificationService.notifyOnOpportunity(SubscriptionChannel.EMAIL, null, moderator.getEmail(), savedOpportunity)
        );
    }


    // TODO: refactor - extract logic
    private Opportunity convertToOpportunity(FullMessageDTO fullMessageDTO) {
        var rawMessageData = fullMessageDTO.rawMessageDTO();
        var processedMessageData = fullMessageDTO.processedMessageDTO();

        Set<Category> categories = new HashSet<>();
        processedMessageData.categories().forEach(categoryName -> {
            Category category = categoryService.getCategoryByName(categoryName);
            categories.add(category);
        });
        Format format = formatService.getFormatByName(processedMessageData.format());

        Opportunity opportunity = new Opportunity();
        opportunity.setTitle(processedMessageData.title());
        opportunity.setCategories(categories);
        opportunity.setFormats(Set.of(format));
        opportunity.setIsAsap(processedMessageData.asap());
        opportunity.setDescription(rawMessageData.messageText());
        opportunity.setSourceName(rawMessageData.channelName());
        opportunity.setSourceLink(TELEGRAM_URL + rawMessageData.channelId());
        opportunity.setPostCreatedDatetime(getLocalDateTime(rawMessageData.postCreationTime()));
        opportunity.setPostScrapperDatetime(getLocalDateTime(rawMessageData.scrappedCreationTime()));
        opportunity.setUuid(UUID.randomUUID());
        opportunity.setOpportunityStatus(OpportunityStatus.PROCESSED);

        return opportunity;
    }

}
