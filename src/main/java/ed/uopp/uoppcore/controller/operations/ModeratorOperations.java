package ed.uopp.uoppcore.controller.operations;

import ed.uopp.uoppcore.data.http.ModeratorSignInDTO;
import ed.uopp.uoppcore.data.http.OpportunityDTO;
import ed.uopp.uoppcore.data.http.OpportunityStatusDTO;
import ed.uopp.uoppcore.entity.OpportunityStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Moderator", description = "Moderator management operations")
public interface ModeratorOperations {
    @Operation(summary = "Approve or reject an opportunity",
            description = "Allows moderators to approve or reject opportunities based on UUID and the desired status.",
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponse(responseCode = "200", description = "Opportunity status successfully updated",
            content = @Content(schema = @Schema(implementation = OpportunityStatus.class)))
    ResponseEntity<OpportunityStatus> approveOpportunity(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Status details for updating an opportunity") OpportunityStatusDTO opportunityStatusDTO);

    @Operation(summary = "Fetch paginated opportunities",
            description = "Fetches paginated opportunities that have been processed, allows pagination through page index and size.")
    @ApiResponse(responseCode = "200", description = "List of paginated opportunities returned",
            content = @Content(schema = @Schema(implementation = OpportunityDTO.class)))
    ResponseEntity<List<OpportunityDTO>> getOpportunities(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "12", required = false) int size);

    @Operation(summary = "Moderator login",
            description = "Authenticates a moderator and returns a JWT token if successful.")
    @ApiResponse(responseCode = "200", description = "Authentication successful, JWT token returned",
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "401", description = "Authentication failed")
    ResponseEntity<String> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Moderator's sign-in credentials") ModeratorSignInDTO moderatorSignInDto);
}