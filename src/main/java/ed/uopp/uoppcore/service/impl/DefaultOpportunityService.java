package ed.uopp.uoppcore.service.impl;

import ed.uopp.uoppcore.entity.Category;
import ed.uopp.uoppcore.entity.Format;
import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.entity.OpportunityStatus;
import ed.uopp.uoppcore.repository.CategoryRepository;
import ed.uopp.uoppcore.repository.FormatRepository;
import ed.uopp.uoppcore.repository.OpportunityRepository;
import ed.uopp.uoppcore.service.OpportunityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class DefaultOpportunityService implements OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final CategoryRepository categoryRepository;
    private final FormatRepository formatRepository;

    @Override
    @Transactional
    public Opportunity save(Opportunity opportunity) {
        // TODO: remove this, only temporary
        Set<Category> managedCategories = opportunity.getCategories().stream()
                .map(category -> categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Category not found: " + category.getId())))
                .collect(Collectors.toSet());
        opportunity.setCategories(managedCategories);
        Set<Format> managedFormats = opportunity.getFormats().stream()
                .map(format -> formatRepository.findById(format.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Category not found: " + format.getId())))
                .collect(Collectors.toSet());
        opportunity.setFormats(managedFormats);

        return opportunityRepository.save(opportunity);
    }

    @Override
    public List<Opportunity> findOpportunities(String fullText, List<String> categories, Boolean isAsap, List<String> formats) {
        return opportunityRepository.findWithFilters(fullText, categories, isAsap, formats);
    }

    @Override
    public OpportunityStatus updateStatus(UUID uuid, OpportunityStatus opportunityStatus) {
        Opportunity opportunity = opportunityRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Opportunity not found!"));
        opportunity.setOpportunityStatus(opportunityStatus);
        opportunityRepository.save(opportunity);
        return opportunity.getOpportunityStatus();
    }

    @Override
    public List<Opportunity> getOpportunitiesCreatedAfter(LocalDateTime fiveMinutesAgo) {
        return opportunityRepository.findByCreatedDatetimeAfterAndOpportunityStatus(fiveMinutesAgo, OpportunityStatus.APPROVED);
    }

}
