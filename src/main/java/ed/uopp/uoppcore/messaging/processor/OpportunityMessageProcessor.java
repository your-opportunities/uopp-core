package ed.uopp.uoppcore.messaging.processor;

import ed.uopp.uoppcore.data.FullMessageData;

public interface OpportunityMessageProcessor {

    void processOpportunityMessage(FullMessageData fullMessageData);

}
