package ed.uopp.uoppcore.data;


import com.fasterxml.jackson.annotation.JsonProperty;

public record FullMessageData(
        @JsonProperty("raw_message_data")
        RawMessageData rawMessageData,
        @JsonProperty("processed_message_data")
        ProcessedMessageData processedMessageData
) {
}