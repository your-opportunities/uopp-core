package ed.uopp.uoppcore.data.http;

import ed.uopp.uoppcore.entity.OpportunityStatus;

import java.util.Set;

public record OpportunityDTO(
        String uuid,
        String title,
        Set<CategoryDTO> categories,
        Set<FormatDTO> formats,
        boolean isAsap,
        String description,
        String sourceName,
        String sourceLink,
        OpportunityStatus opportunityStatus
) {
}
