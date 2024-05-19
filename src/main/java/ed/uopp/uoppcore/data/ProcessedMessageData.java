package ed.uopp.uoppcore.data;

import java.util.List;

public record ProcessedMessageData(
        String title,
        List<String> categories,
        String format,
        boolean asap
) {
}
