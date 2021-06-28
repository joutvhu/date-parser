package com.joutvhu.date.parser;

import com.joutvhu.date.parser.convertor.*;
import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.exception.ParseException;
import com.joutvhu.date.parser.strategy.StrategyFactory;
import com.joutvhu.date.parser.support.DateFormat;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public class DateParser {
    private static DateParser INSTANCE;

    private Locale defaultLocale;
    private TimeZone defaultZone;
    private Map<Class<?>, Convertor<?>> convertors;
    private StrategyFactory strategyFactory = StrategyFactory.INSTANCE;

    public DateParser() {
        this.convertors = new HashMap<>();
    }

    public static DateParser instance() {
        if (INSTANCE == null) {
            INSTANCE = new DateParser()
                    .convertor(CalendarConvertor.INSTANCE)
                    .convertor(DateConvertor.INSTANCE)
                    .convertor(LocalDateConvertor.INSTANCE)
                    .convertor(LocalDateTimeConvertor.INSTANCE)
                    .convertor(LocalTimeConvertor.INSTANCE)
                    .convertor(InstantConvertor.INSTANCE)
                    .convertor(SqlDateConvertor.INSTANCE)
                    .convertor(SqlTimeConvertor.INSTANCE)
                    .convertor(SqlTimestampConvertor.INSTANCE)
                    .convertor(DayOfWeekConvertor.INSTANCE)
                    .convertor(LongConvertor.INSTANCE)
                    .convertor(MonthConvertor.INSTANCE)
                    .convertor(MonthDayConvertor.INSTANCE)
                    .convertor(OffsetDateTimeConvertor.INSTANCE)
                    .convertor(OffsetTimeConvertor.INSTANCE)
                    .convertor(TimeZoneConvertor.INSTANCE)
                    .convertor(YearConvertor.INSTANCE)
                    .convertor(YearMonthConvertor.INSTANCE)
                    .convertor(ZonedDateTimeConvertor.INSTANCE)
                    .convertor(ZoneOffsetConvertor.INSTANCE);
        }
        return INSTANCE;
    }

    public <T> DateParser convertor(Class<T> typeOfConvertor, Convertor<T> convertor) {
        this.convertors.put(typeOfConvertor, convertor);
        return this;
    }

    public <T> DateParser convertor(Convertor<T> convertor) {
        Class<T> typeOfConvertor = Convertor.typeOfConvertor(convertor);
        Objects.requireNonNull(typeOfConvertor);
        return this.convertor(typeOfConvertor, convertor);
    }

    public DateParser locale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
        return this;
    }

    public DateParser zone(TimeZone defaultZone) {
        this.defaultZone = defaultZone;
        return this;
    }

    /**
     * Load a custom {@link StrategyFactory}
     */
    public DateParser strategyFactory(StrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
        return this;
    }

    public <T> T convert(Class<T> type, DateBuilder builder) {
        if (this.convertors.containsKey(type)) {
            Convertor<T> convertor = (Convertor<T>) this.convertors.get(type);
            if (convertor != null)
                return convertor.convert(builder);
        }

        throw new ClassCastException("Unsupported " + type.getName() + " class.");
    }

    public <T> T parse(Class<T> type, String value, String... patterns) {
        if (type == null)
            throw new IllegalArgumentException("Date Type must not be null");
        if (value == null || patterns == null)
            throw new IllegalArgumentException("Date and Patterns must not be null");

        DateBuilder builder;
        Throwable cause = null;
        for (final String pattern : patterns) {
            DateFormat dateFormat = new DateFormat(pattern, defaultLocale, defaultZone, strategyFactory);
            try {
                builder = dateFormat.parse(value);
            } catch (Exception e) {
                cause = e;
                continue;
            }
            return this.convert(type, builder);
        }

        throw new ParseException("Unable to parse the date: " + value, cause, patterns);
    }

    public static <T> T quickParse(Class<T> type, String value, String... patterns) {
        return DateParser.instance().parse(type, value, patterns);
    }
}
