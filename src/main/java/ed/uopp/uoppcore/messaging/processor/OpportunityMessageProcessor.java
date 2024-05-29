package ed.uopp.uoppcore.messaging.processor;

import ed.uopp.uoppcore.data.mq.FullMessageDTO;

public interface OpportunityMessageProcessor {

    void processOpportunityMessage(FullMessageDTO fullMessageDTO);

}
