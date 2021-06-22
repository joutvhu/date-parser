package com.joutvhu.date.parser.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.TimeZone;

@Getter
@Setter
public class DateStorage {
    private Integer year;
    private Integer month;
    private Integer day;

    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer nano;

    private Locale locale;
    private TimeZone zone;

    public DateStorage(Locale locale, TimeZone zone) {
        this.locale = locale;
        this.zone = zone;
    }
}
