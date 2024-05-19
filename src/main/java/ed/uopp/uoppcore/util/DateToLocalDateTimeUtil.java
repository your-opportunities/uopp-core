package ed.uopp.uoppcore.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class DateToLocalDateTimeUtil {

    private DateToLocalDateTimeUtil() {
    }

    public static LocalDateTime getLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault());
    }

}
