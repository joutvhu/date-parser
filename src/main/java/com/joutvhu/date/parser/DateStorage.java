package com.joutvhu.date.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.TimeZone;

@Getter
@Setter
public class DateStorage {
    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;
    private int second;
    private int nano;

    private Locale locale;
    private TimeZone zone;

    public DateStorage(Locale locale, TimeZone zone) {
        this.locale = locale;
        this.zone = zone;
    }
}
