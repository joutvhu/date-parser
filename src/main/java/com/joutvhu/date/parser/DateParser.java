package com.joutvhu.date.parser;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateParser {
    private DateFormatter formatter;

    /**
     * Get default instance of {@link DateFormatter}.
     *
     * @return The date parser.
     */
    public DateFormatter formatter() {
        if (formatter == null)
            formatter = new DateFormatter();
        return formatter;
    }

    public <T> String format(T object, String pattern) {
        return formatter().format(object, pattern);
    }

    /**
     * Quick parse with default {@link DateParser#formatter()}
     *
     * @param type     is the target type class
     * @param value    is string of date need to parse
     * @param patterns are possible formats of the value
     * @param <T>      is the target type
     * @return target object
     */
    public static <T> T parse(Class<T> type, String value, String... patterns) {
        return formatter().parse(type, value, patterns);
    }
}
