package ed.uopp.uoppcore.controller.operations;


import ed.uopp.uoppcore.data.http.SubscriptionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Subscriptions", description = "The Subscriptions API, responsible for managing user subscriptions")
public interface SubscriptionOperations {

    @Operation(summary = "Create a new subscription",
            description = "Create a new subscription based on the provided subscription details.")
    @ApiResponse(responseCode = "200", description = "Subscription created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid data provided")
    ResponseEntity<Void> createSubscription(
            @RequestBody @Parameter(description = "Subscription data transfer object containing all necessary subscription details") SubscriptionDTO subscriptionDTO);
}
