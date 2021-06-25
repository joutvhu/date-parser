package com.joutvhu.date.parser.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

@Getter
@Setter
public class DateBuilder {
    private Integer year;
    private Integer month;
    private Integer day;

    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer nano;

    private Locale locale;
    private TimeZone zone;

    private Map<String, Object> extension;

    public DateBuilder(Locale locale, TimeZone zone) {
        this.locale = locale;
        this.zone = zone;
        this.extension = new HashMap<>();
    }

    public void put(String key, Object value) {
        this.extension.put(key, value);
    }

    public <T> T get(String key) {
        return (T) this.extension.get(key);
    }
}
