package ed.uopp.uoppcore.messaging.processor.impl;

import ed.uopp.uoppcore.data.FullMessageData;
import ed.uopp.uoppcore.entity.OpportunityStatus;
import ed.uopp.uoppcore.entity.Category;
import ed.uopp.uoppcore.entity.Format;
import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.messaging.processor.OpportunityMessageProcessor;
import ed.uopp.uoppcore.service.CategoryService;
import ed.uopp.uoppcore.service.FormatService;
import ed.uopp.uoppcore.service.NotificationService;
import ed.uopp.uoppcore.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static ed.uopp.uoppcore.util.DateToLocalDateTimeUtil.getLocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultOpportunityMessageProcessor implements OpportunityMessageProcessor {

    private final CategoryService categoryService;
    private final FormatService formatService;
    private final OpportunityService opportunityService;
    private final NotificationService notificationService;

    @Override
    public void processOpportunityMessage(FullMessageData fullMessageData) {
        Opportunity opportunity = convertToOpportunity(fullMessageData);
        Opportunity savedOpportunity = opportunityService.save(opportunity);
        log.info("Processed and saved opportunity");

//        TODO: functionality for moderator to approve opportunity
        notificationService.notifyOnOpportunityCreation();
//        log.info("Notified moderator with a new approval request");
    }


    // TODO: refactor - extract logic
    private Opportunity convertToOpportunity(FullMessageData fullMessageData) {
        var rawMessageData = fullMessageData.rawMessageData();
        var processedMessageData = fullMessageData.processedMessageData();

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
        opportunity.setSource(rawMessageData.channelName());
        opportunity.setPostCreatedDatetime(getLocalDateTime(rawMessageData.postCreationTime()));
        opportunity.setPostScrapperDatetime(getLocalDateTime(rawMessageData.scrappedCreationTime()));
        opportunity.setUuid(UUID.randomUUID());
        opportunity.setOpportunityStatus(OpportunityStatus.PROCESSED);

        return opportunity;
    }

}
