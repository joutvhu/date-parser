package com.joutvhu.date.parser.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

@Getter
@Setter
public class DateBuilder {
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static final String SECOND = "second";
    public static final String NANO = "nano";
    public static final String LOCALE = "locale";
    public static final String ZONE = "zone";

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
    private List<DateListener> listeners;

    public DateBuilder(Locale locale, TimeZone zone) {
        this.locale = locale;
        this.zone = zone;
        this.extension = new HashMap<>();
    }

    public void setYear(Integer year) {
        this.year = year;
        this.dispatch(YEAR, year);
    }

    public void setMonth(Integer month) {
        this.month = month;
        this.dispatch(MONTH, month);
    }

    public void setDay(Integer day) {
        this.day = day;
        this.dispatch(DAY, day);
    }

    public void setHour(Integer hour) {
        this.hour = hour;
        this.dispatch(HOUR, hour);
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
        this.dispatch(MINUTE, minute);
    }

    public void setSecond(Integer second) {
        this.second = second;
        this.dispatch(SECOND, second);
    }

    public void setNano(Integer nano) {
        this.nano = nano;
        this.dispatch(NANO, nano);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        this.dispatch(LOCALE, locale);
    }

    public void setZone(TimeZone zone) {
        this.zone = zone;
        this.dispatch(ZONE, zone);
    }

    public void put(String key, Object value) {
        this.extension.put(key, value);
        this.dispatch(key, value);
    }

    public <T> T get(String key) {
        return (T) this.extension.get(key);
    }

    private void dispatch(String key, Object value) {
        if (this.listeners != null)
            for (DateListener listener : this.listeners)
                listener.changed(this, key, value);
    }
}
