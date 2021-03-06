package com.joutvhu.date.parser.domain;

import com.joutvhu.date.parser.subscription.Subscription;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.temporal.WeekFields;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * {@link ObjectiveDate} is a temporary date storage object.
 * It is available to convert to other data types through a {@link com.joutvhu.date.parser.convertor.Convertor}.
 *
 * @author Giao Ho
 * @version 1.0.0
 * @since 2021-07-12
 */
@Getter
@Setter
@SuppressWarnings("java:S1845")
public class ObjectiveDate implements Serializable {
    private static final long serialVersionUID = -8615336398200738252L;

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
    private WeekFields weekFields;

    @Getter(AccessLevel.PRIVATE)
    @SuppressWarnings("java:S1948")
    private final List<Tracer> tracers;

    @Getter(AccessLevel.PRIVATE)
    @SuppressWarnings("java:S1948")
    private final Map<String, Object> extension;

    @Getter(AccessLevel.PRIVATE)
    @SuppressWarnings("java:S1948")
    private final Map<Class<? extends Subscription>, Subscription> listeners;

    public ObjectiveDate(Locale locale, TimeZone zone) {
        this(locale, zone, WeekFields.of(locale != null ? locale : Locale.getDefault()));
    }

    public ObjectiveDate(Locale locale, TimeZone zone, WeekFields weekFields) {
        this.locale = locale != null ? locale : Locale.getDefault();
        this.zone = zone != null ? zone : TimeZone.getDefault();
        this.weekFields = weekFields != null ? weekFields : WeekFields.ISO;
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
                if (!(value instanceof Locale))
                    throw new IllegalArgumentException("Invalid Locale: " + value);
                this.setLocale((Locale) value);
                break;
            case ZONE:
                if (!(value instanceof TimeZone))
                    throw new IllegalArgumentException("Invalid TimeZone: " + value);
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
            for (Subscription listener : this.listeners.values())
                listener.changed(this, key, value);
        }
    }

    public void subscribe(Subscription listener) {
        if (!this.listeners.containsKey(listener.getClass()))
            this.listeners.put(listener.getClass(), listener);
    }

    public void unsubscribe(Class<? extends Subscription> listener) {
        this.listeners.remove(listener);
    }

    public DateBackup backup() {
        return new DateBackup(this);
    }

    public interface Tracer {
        List<Map.Entry<String, Object>> share();

        void trace(String key, Object oldValue);
    }

    @Getter
    public static class DateBackup implements Backup<ObjectiveDate>, Tracer {
        private final int startAt;
        private final ObjectiveDate objective;
        private final List<Map.Entry<String, Object>> diary;

        public DateBackup(ObjectiveDate objective) {
            this.objective = objective;

            if (objective.tracers != null && !objective.tracers.isEmpty()) {
                this.diary = objective.tracers.get(0).share();
                this.startAt = objective.tracers.size();
            } else {
                this.diary = new ArrayList<>();
                this.startAt = 0;
            }

            this.objective.tracers.add(this);
        }

        @Override
        public List<Map.Entry<String, Object>> share() {
            return diary;
        }

        @Override
        public void trace(String key, Object oldValue) {
            this.diary.add(new AbstractMap.SimpleEntry<>(key, oldValue));
        }

        @Override
        public ObjectiveDate restore() {
            for (int i = this.diary.size() - 1; i >= this.startAt; i--) {
                Map.Entry<String, Object> pair = this.diary.get(i);
                this.objective.set(pair.getKey(), pair.getValue(), false, false);
                this.diary.remove(i);
            }
            this.objective.tracers.remove(this);
            return objective;
        }

        @Override
        public void commit() {
            this.objective.tracers.remove(this);
        }
    }
}
