package ed.uopp.uoppcore.service;

import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.entity.OpportunityStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OpportunityService {

    Opportunity save(Opportunity opportunity);

    List<Opportunity> findOpportunities(String fullText, List<String> categories, Boolean isAsap, List<String> formats);

    OpportunityStatus updateStatus(UUID uuid, OpportunityStatus opportunityStatus);

    List<Opportunity> getOpportunitiesCreatedAfter(LocalDateTime fiveMinutesAgo);
}
