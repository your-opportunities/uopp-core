package ed.uopp.uoppcore.controller.operations;


import ed.uopp.uoppcore.data.http.SubscriptionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Subscriptions", description = "The Subscriptions API, responsible for managing user subscriptions")
public interface SubscriptionOperations {

    @Operation(summary = "Create a new subscription",
            description = "Create a new subscription based on the provided subscription details.")
    @ApiResponse(responseCode = "200", description = "Subscription created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid data provided")
    ResponseEntity<UUID> createSubscription(
            @RequestBody @Parameter(description = "Subscription data transfer object containing all necessary subscription details") SubscriptionDTO subscriptionDTO);

    @Operation(summary = "Get an existing subscription",
            description = "Fetch details of an existing subscription using the provided UUID.")
    @ApiResponse(responseCode = "200", description = "Subscription fetched successfully")
    @ApiResponse(responseCode = "404", description = "Subscription not found")
    ResponseEntity<SubscriptionDTO> getSubscription(@Parameter(description = "UUID of the subscription") UUID uuid);

    @Operation(summary = "Delete a subscription",
            description = "Delete a subscription using the provided UUID.")
    @ApiResponse(responseCode = "200", description = "Subscription deleted successfully")
    @ApiResponse(responseCode = "404", description = "Subscription not found")
    ResponseEntity<Void> deleteSubscription(@Parameter(description = "UUID of the subscription") UUID uuid);

}
