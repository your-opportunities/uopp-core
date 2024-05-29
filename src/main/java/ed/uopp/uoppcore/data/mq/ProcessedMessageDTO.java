package ed.uopp.uoppcore.data.mq;

import java.util.List;

public record ProcessedMessageDTO(
        String title,
        List<String> categories,
        String format,
        boolean asap
) {
}
