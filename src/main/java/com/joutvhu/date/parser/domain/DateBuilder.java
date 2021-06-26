package com.joutvhu.date.parser.domain;

import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    @Getter(AccessLevel.PRIVATE)
    private final List<Tracer> tracers;

    @Getter(AccessLevel.PRIVATE)
    private final Map<String, Object> extension;

    @Getter(AccessLevel.PRIVATE)
    private final Map<Class<? extends DateSubscription>, DateSubscription> listeners;

    public DateBuilder(Locale locale, TimeZone zone) {
        this.locale = locale;
        this.zone = zone;
        this.tracers = new ArrayList<>();
        this.extension = new HashMap<>();
        this.listeners = new HashMap<>();
    }

    public void set(String key, Object value) {
        this.set(key, value, true, true);
    }

    public void set(String key, Object value, boolean dispatch, boolean trace) {
        if (trace)
            this.trace(key);

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

        if (dispatch)
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

    protected void trace(String key) {
        if (!this.tracers.isEmpty())
            this.tracers.get(0).trace(key, this.get(key));
    }

    protected void dispatch(String key, Object value) {
        if (this.listeners != null) {
            for (DateSubscription listener : this.listeners.values())
                listener.changed(this, key, value);
        }
    }

    public void subscribe(DateSubscription listener) {
        if (!this.listeners.containsKey(listener.getClass()))
            this.listeners.put(listener.getClass(), listener);
    }

    public void unsubscribe(Class<? extends DateSubscription> listener) {
        this.listeners.remove(listener);
    }

    public DateBackup backup() {
        return new DateBackup(this);
    }

    public interface Tracer {
        List<Pair<String, Object>> share();

        void trace(String key, Object oldValue);
    }

    @Getter
    public static class DateBackup implements Backup<DateBuilder>, Tracer {
        private final int startAt;
        private final DateBuilder builder;
        private final List<Pair<String, Object>> diary;

        public DateBackup(DateBuilder builder) {
            this.builder = builder;

            if (builder.tracers != null && !builder.tracers.isEmpty()) {
                this.diary = builder.tracers.get(0).share();
                this.startAt = builder.tracers.size();
            } else {
                this.diary = new ArrayList<>();
                this.startAt = 0;
            }

            this.builder.tracers.add(this);
        }

        @Override
        public List<Pair<String, Object>> share() {
            return diary;
        }

        @Override
        public void trace(String key, Object oldValue) {
            this.diary.add(new Pair<>(key, oldValue));
        }

        @Override
        public DateBuilder restore() {
            for (int i = this.diary.size() - 1; i >= this.startAt; i--) {
                Pair<String, Object> pair = this.diary.get(i);
                this.builder.set(pair.getKey(), pair.getValue(), false, false);
                this.diary.remove(i);
            }
            this.builder.tracers.remove(this);
            return builder;
        }

        @Override
        public void commit() {
            this.builder.tracers.remove(this);
        }
    }
}
