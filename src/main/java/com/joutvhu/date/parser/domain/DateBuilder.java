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
    private Map<Class<? extends DateSubscription>, DateSubscription> listeners;

    public DateBuilder(Locale locale, TimeZone zone) {
        this.locale = locale;
        this.zone = zone;
        this.extension = new HashMap<>();
    }

    public void set(String key, Object value) {
        switch (key) {
            case YEAR:
                this.setYear((Integer) value);
                break;
            case MONTH:
                this.setMonth((Integer) value);
                break;
            case DAY:
                this.setDay((Integer) value);
                break;
            case HOUR:
                this.setHour((Integer) value);
                break;
            case MINUTE:
                this.setMinute((Integer) value);
                break;
            case SECOND:
                this.setSecond((Integer) value);
                break;
            case NANO:
                this.setNano((Integer) value);
                break;
            case LOCALE:
                assert value instanceof Locale;
                this.setLocale((Locale) value);
                break;
            case ZONE:
                assert value instanceof TimeZone;
                this.setZone((TimeZone) value);
                break;
            default:
                this.extension.put(key, value);
                break;
        }
        this.dispatch(key, value);
    }

    public <T> T get(String key) {
        switch (key) {
            case YEAR:
                return (T) this.getYear();
            case MONTH:
                return (T) this.getMonth();
            case DAY:
                return (T) this.getDay();
            case HOUR:
                return (T) this.getHour();
            case MINUTE:
                return (T) this.getMinute();
            case SECOND:
                return (T) this.getSecond();
            case NANO:
                return (T) this.getNano();
            case LOCALE:
                return (T) this.getLocale();
            case ZONE:
                return (T) this.getZone();
            default:
                return (T) this.extension.get(key);
        }
    }

    public void dispatch(String key, Object value) {
        if (this.listeners != null)
            this.listeners.forEach((aClass, listener) -> listener.changed(this, key, value));
    }

    public void subscribe(DateSubscription listener) {
        if (!this.listeners.containsKey(listener.getClass()))
            this.listeners.put(listener.getClass(), listener);
    }

    public void unsubscribe(Class<? extends DateSubscription> listener) {
        this.listeners.remove(listener);
    }
}
