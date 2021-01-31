package io.github.andreepdias.clickbus.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HandleDate {

    public static LocalDateTime StringToLocalDateTime(String dateTime){
        DateTimeFormatter formatter = getDateTimeFormatter();
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static String LocalDateTimeToString(LocalDateTime dateTime){
        DateTimeFormatter formatter = getDateTimeFormatter();
        return dateTime.format(formatter);
    }

    private static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    }

}
