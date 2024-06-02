package ed.uopp.uoppcore.service.impl;

import ed.uopp.uoppcore.data.mq.NotificationDTO;
import ed.uopp.uoppcore.data.mq.NotificationType;
import ed.uopp.uoppcore.entity.*;
import ed.uopp.uoppcore.mapper.OpportunityMapper;
import ed.uopp.uoppcore.messaging.producer.NotificationProducer;
import ed.uopp.uoppcore.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultNotificationService implements NotificationService {

    private final NotificationProducer notificationProducer;

    @Override
    public void notifyOnOpportunity(SubscriptionChannel subscriptionChannel, UUID uuid, String userId, Opportunity opportunity) {
        NotificationType template = getNotificationTemplateFromStatus(opportunity.getOpportunityStatus());

        if (template == null) return;

        NotificationDTO notificationDTO = createNotification(userId, uuid, template, opportunity);
        notificationProducer.sendMessage(notificationDTO, subscriptionChannel);
    }

    private NotificationType getNotificationTemplateFromStatus(OpportunityStatus opportunityStatus) {
        return switch (opportunityStatus) {
            case APPROVED -> NotificationType.USER;
            case PROCESSED -> NotificationType.MODERATOR;
            default -> null;
        };
    }

    private NotificationDTO createNotification(String userId, UUID uuid, NotificationType template, Opportunity opportunity) {
        String opportunityText = "**Назва**: " + opportunity.getTitle()
                + "\n\n**Категорії**: " + categorySetToString(opportunity.getCategories())
                + "\n\n**Формат**: " + formatSetToString(opportunity.getFormats())
                + "\n\n**Терміновість**: " + getAsapText(opportunity.getIsAsap())
                + "\n\n**Текст з джерела**:\n\n" + opportunity.getDescription();
        String opportunityLink = "http://localhost:8080/opportunities/" + opportunity.getUuid();
        String opportunitySourceLink = opportunity.getSourceLink();

        return new NotificationDTO(
                userId,
                uuid,
                template,
                opportunityText,
                opportunityLink,
                opportunitySourceLink
        );
    }

    // optimize, refactor: todo
    private static String getAsapText(Boolean asap) {
        return asap ? "термінові" : "будь-які";
    }

    private String formatSetToString(Set<Format> formats) {
        return StringUtils.join(formats.stream().map(Format::getName).collect(Collectors.toList()), ", ");
    }

    private String categorySetToString(Set<Category> formats) {
        return StringUtils.join(formats.stream().map(Category::getName).collect(Collectors.toList()), ", ");
    }

}
