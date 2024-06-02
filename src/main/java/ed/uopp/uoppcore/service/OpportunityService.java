package ed.uopp.uoppcore.service;

import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.entity.OpportunityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OpportunityService {

    Opportunity save(Opportunity opportunity);

    Page<Opportunity> findOpportunities(String fullText, List<String> categories, Boolean isAsap, List<String> formats, Pageable pageable);

    Optional<Opportunity> findOpportunityByUuid(UUID uuid);

    OpportunityStatus updateStatus(UUID uuid, OpportunityStatus opportunityStatus);

    List<Opportunity> getOpportunitiesUpdatedAfter(LocalDateTime dateTimeThreshold);
}
