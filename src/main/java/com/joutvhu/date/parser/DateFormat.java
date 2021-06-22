package com.joutvhu.date.parser;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.strategy.Strategy;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormat {
    private Locale locale;
    private TimeZone zone;
    private List<Strategy> strategies;
    private int endIndex;

    public DateFormat(String pattern) {
        this(pattern, Locale.getDefault(), TimeZone.getDefault());
    }

    public DateFormat(String pattern, Locale locale, TimeZone zone) {
        if (locale == null || zone == null)
            throw new IllegalArgumentException("Locale and TimeZone must not be null");

        this.locale = locale;
        this.zone = zone;
        this.strategies = new DatePatternSplitter(pattern).getStrategyChain();
        this.endIndex = this.strategies.size() - 1;
    }

    private void parse(DateStorage storage, StringSource source, int index) {
        if (index < this.endIndex)
            this.strategies.get(index).parse(storage, source, () -> this.parse(storage, source, index + 1));
        else if (index == this.endIndex)
            this.strategies.get(index).parse(storage, source, null);
    }

    public DateStorage parse(String value) {
        DateStorage storage = new DateStorage(this.locale, this.zone);
        StringSource source = new StringSource(value);
        this.parse(storage, source, 0);
        return storage;
    }
}
