package com.joutvhu.date.parser;

public interface Formatter {
    <T> T parse(Class<T> type, String value, String... patterns);

    <T> String format(T object, String pattern);
}
