package es.javierdearcos.bookstore.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public Date getDateWithoutTime(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
