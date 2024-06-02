package ed.uopp.uoppcore.repository;

import ed.uopp.uoppcore.entity.Category;
import ed.uopp.uoppcore.entity.Format;
import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.entity.OpportunityStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long>, JpaSpecificationExecutor<Opportunity> {

    static Specification<Opportunity> hasCategory(List<String> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Opportunity, Category> categoryJoin = root.join("categories", JoinType.INNER);
            return categoryJoin.get("name").in(categories);
        };
    }

    static Specification<Opportunity> hasFormat(List<String> formats) {
        return (root, query, criteriaBuilder) -> {
            if (formats == null || formats.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Opportunity, Format> categoryJoin = root.join("formats", JoinType.INNER);
            return categoryJoin.get("name").in(formats);
        };
    }

    static Specification<Opportunity> isAsap(Boolean isAsap) {
        return (root, query, criteriaBuilder) ->
                isAsap == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("isAsap"), isAsap);
    }

    static Specification<Opportunity> fullTextSearch(String text) {
        return (root, query, criteriaBuilder) -> {
            if (text == null || text.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + text.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + text.toLowerCase() + "%")
            );
        };
    }

    default Page<Opportunity> findWithFilters(String text, List<String> categories, Boolean isAsap, List<String> formats, Pageable pageable) {
        Specification<Opportunity> spec = Specification.where(hasCategory(categories))
                .and(hasFormat(formats))
                .and(isAsap(isAsap))
                .and(fullTextSearch(text));
        return findAll(spec, pageable);
    }

    Optional<Opportunity> findByUuid(UUID uuid);

    List<Opportunity> findByStatusUpdateDatetimeAfterAndOpportunityStatus(LocalDateTime secondsAgo, OpportunityStatus status);

}