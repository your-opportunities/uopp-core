package ed.uopp.uoppcore.data.mq;


import com.fasterxml.jackson.annotation.JsonProperty;

public record FullMessageDTO(
        @JsonProperty("raw_message_data")
        RawMessageDTO rawMessageDTO,
        @JsonProperty("processed_message_data")
        ProcessedMessageDTO processedMessageDTO
) {
}