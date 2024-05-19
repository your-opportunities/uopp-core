package ed.uopp.uoppcore.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record RawMessageData(

        @JsonProperty("post_creation_time")
        Date postCreationTime,
        @JsonProperty("scrapped_creation_time")
        Date scrappedCreationTime,
        @JsonProperty("channel_id")
        long channelId,
        @JsonProperty("channel_name")
        String channelName,
        @JsonProperty("message_text")
        String messageText
) {
}