package com.mjc.school.util;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Formatting {

    public static final SimpleDateFormat SIMPLEDATEFORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
}
