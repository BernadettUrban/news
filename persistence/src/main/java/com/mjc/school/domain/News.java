package com.mjc.school.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class News {

private Long id;
private  String title;
private String content;
private Author author;
private  Date created;
    private  Date modified;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    String nowAsISO = df.format(new Date());
}
