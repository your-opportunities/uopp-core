package ed.uopp.uoppcore.data.http;

import ed.uopp.uoppcore.entity.OpportunityStatus;

import java.util.UUID;

public record OpportunityStatusDTO(UUID uuid, OpportunityStatus opportunityStatus) {
}
