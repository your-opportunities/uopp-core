package ed.uopp.uoppcore.data.http;

import java.util.Set;

public record SubscriptionDTO(
        String subscriptionChannel,
        String userId,
        Set<String> categories,
        Set<String> formats,
        boolean isAsap
) {
}
