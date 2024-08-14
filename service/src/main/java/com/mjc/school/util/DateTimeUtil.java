package com.mjc.school.util;

import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private DateTimeUtil() {
        // Prevent instantiation
    }

    @Named("localDateTimeToString")
    public static String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(ISO_FORMATTER) : null;
    }

    @Named("stringToLocalDateTime")
    public static LocalDateTime stringToLocalDateTime(String dateTime) {
        return dateTime != null ? LocalDateTime.parse(dateTime, ISO_FORMATTER) : null;
    }
}

