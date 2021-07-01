package com.joutvhu.date.parser;

import com.joutvhu.date.parser.convertor.Convertor;
import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.exception.ParseException;
import com.joutvhu.date.parser.strategy.StrategyFactory;
import com.joutvhu.date.parser.support.ConvertorService;
import com.joutvhu.date.parser.support.DateFormat;

import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

/**
 * The date parser
 *
 * @author Giao Ho
 * @since 1.0.0
 */
public class DateParser implements DateParseAndFormat {
    private static DateParser instance;

    private Locale defaultLocale;
    private TimeZone defaultZone;
    private WeekFields defaultWeekFields;
    private final Map<Class<?>, Convertor<?>> convertors;
    private StrategyFactory strategyFactory = StrategyFactory.INSTANCE;

    public DateParser() {
        this.convertors = new HashMap<>();
    }

    /**
     * Get default instance of {@link DateParser}.
     *
     * @return The date parser.
     */
    public static DateParser getInstance() {
        if (instance == null)
            instance = new DateParser();
        return instance;
    }

    /**
     * Set a convertor for new target type
     *
     * @param typeOfConvertor is target type class
     * @param convertor       is Convertor for the target type
     * @param <T>             is target type
     * @return The current date parser.
     */
    public <T> DateParser with(Class<T> typeOfConvertor, Convertor<T> convertor) {
        this.convertors.put(typeOfConvertor, convertor);
        return this;
    }

    /**
     * Set a convertor for new Target Type
     *
     * @param convertor is Convertor for the target type
     * @param <T>       is target type
     * @return The current date parser.
     */
    public <T> DateParser with(Convertor<T> convertor) {
        Class<T> typeOfConvertor = Convertor.typeOfConvertor(convertor);
        Objects.requireNonNull(typeOfConvertor);
        return this.with(typeOfConvertor, convertor);
    }

    /**
     * Set default {@link Locale}
     *
     * @param defaultLocale The default locale
     * @return The current date parser.
     */
    public DateParser with(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
        return this;
    }

    /**
     * Set default {@link TimeZone}
     *
     * @param defaultZone The default time zone
     * @return The current date parser.
     */
    public DateParser with(TimeZone defaultZone) {
        this.defaultZone = defaultZone;
        return this;
    }

    /**
     * Set default {@link TimeZone}
     *
     * @param defaultWeekFields The default week fields
     * @return The current date parser.
     */
    public DateParser with(WeekFields defaultWeekFields) {
        this.defaultWeekFields = defaultWeekFields;
        return this;
    }

    /**
     * Load a custom {@link StrategyFactory}
     *
     * @param strategyFactory The default strategy factory
     * @return The current date parser.
     */
    public DateParser with(StrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
        return this;
    }

    private <T> Convertor<T> getConvertor(Class<T> type, boolean forParse) {
        if (this.convertors.containsKey(type)) {
            Convertor<T> convertor = (Convertor<T>) this.convertors.get(type);
            if (convertor != null)
                return convertor;
        }

        Convertor<T> convertor = ConvertorService.getInstance().getConvertor(type, forParse);
        if (convertor != null)
            return convertor;

        return null;
    }

    private <T> T convert(Class<T> type, ObjectiveDate objective) {
        Convertor<T> convertor = this.getConvertor(type, true);
        if (convertor != null)
            return convertor.convert(objective);
        throw new ClassCastException("Unsupported " + type.getName() + " class.");
    }

    private <T> ObjectiveDate convert(ObjectiveDate objective, T object) {
        Class<T> type = (Class<T>) object.getClass();
        Convertor<T> convertor = this.getConvertor(type, false);
        if (convertor != null)
            return convertor.convert(objective, object);
        throw new ClassCastException("Unsupported " + type.getName() + " class.");
    }

    /**
     * Parse the string of date to the target type
     *
     * @param type     is the target type class
     * @param value    is string of date need to parse
     * @param patterns are possible formats of the value
     * @param <T>      is the target type
     * @return target object
     */
    @Override
    public <T> T parse(Class<T> type, String value, String... patterns) {
        if (type == null)
            throw new IllegalArgumentException("Date Type must not be null");
        if (value == null || patterns == null)
            throw new IllegalArgumentException("Date and Patterns must not be null");

        ObjectiveDate objective;
        Throwable cause = null;
        for (final String pattern : patterns) {
            DateFormat dateFormat = new DateFormat(pattern, strategyFactory)
                    .with(defaultLocale)
                    .with(defaultZone)
                    .with(defaultWeekFields);
            try {
                objective = dateFormat.parse(value);
            } catch (Exception e) {
                cause = e;
                continue;
            }
            return this.convert(type, objective);
        }

        throw new ParseException("Unable to parse the date: " + value, cause, patterns);
    }

    /**
     * Quick parse with default {@link DateParser#getInstance()}
     *
     * @param type     is the target type class
     * @param value    is string of date need to parse
     * @param patterns are possible formats of the value
     * @param <T>      is the target type
     * @return target object
     */
    public static <T> T quickParse(Class<T> type, String value, String... patterns) {
        return DateParser.getInstance().parse(type, value, patterns);
    }

    @Override
    public <T> String format(T object, String pattern) {
        if (object == null)
            throw new IllegalArgumentException("Object Date must not be null");
        if (pattern == null)
            throw new IllegalArgumentException("Pattern must not be null");

        ObjectiveDate objective = new ObjectiveDate(this.defaultLocale, this.defaultZone, this.defaultWeekFields);
        objective = this.convert(objective, object);

        return new DateFormat(pattern, strategyFactory)
                .with(defaultLocale)
                .with(defaultZone)
                .with(defaultWeekFields).format(objective);
    }

    public static <T> String quickFormat(T object, String pattern) {
        return DateParser.getInstance().format(object, pattern);
    }
}
