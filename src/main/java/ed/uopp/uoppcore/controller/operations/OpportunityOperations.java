package ed.uopp.uoppcore.controller.operations;

import ed.uopp.uoppcore.data.http.OpportunityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Opportunities", description = "The Opportunities API, responsible for managing opportunities data")
public interface OpportunityOperations {

    @Operation(summary = "Find opportunities with optional filters",
            description = "Retrieve a paginated list of opportunities based on multiple search criteria.")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(schema = @Schema(implementation = OpportunityDTO.class)))
    ResponseEntity<List<OpportunityDTO>> findOpportunities(
            @RequestParam(required = false) @Parameter(description = "Full text search across opportunity properties") String fullText,
            @RequestParam(required = false) @Parameter(description = "Filter by categories") List<String> categories,
            @RequestParam(required = false) @Parameter(description = "Filter by urgency status") Boolean isAsap,
            @RequestParam(required = false) @Parameter(description = "Filter by available formats") List<String> formats,
            @RequestParam(required = false, defaultValue = "0") @Parameter(description = "Page index for pagination") int page,
            @RequestParam(required = false, defaultValue = "12") @Parameter(description = "Number of items per page") int size);

    @Operation(summary = "Get a specific opportunity by UUID",
            description = "Retrieve a single opportunity by its unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(schema = @Schema(implementation = OpportunityDTO.class)))
    @ApiResponse(responseCode = "404", description = "Opportunity not found")
    ResponseEntity<OpportunityDTO> findOpportunity(@PathVariable @Parameter(description = "UUID of the opportunity to fetch") String uuid);

}