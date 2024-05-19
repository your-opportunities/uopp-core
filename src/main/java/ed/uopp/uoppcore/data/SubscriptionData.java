package ed.uopp.uoppcore.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SubscriptionData {
    private String subscriptionChannel;
    private String userId;
    private Set<String> categories;
    private Set<String> formats;
    private Boolean isAsap;

}