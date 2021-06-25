package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.strategy.NextStrategy;
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

    private void parse(DateBuilder builder, StringSource source, int index) {
        if (index < this.endIndex) {
            this.strategies.get(index).parse(builder, source, new NextStrategy() {
                @Override
                public Strategy get() {
                    return DateFormat.this.strategies.get(index + 1);
                }

                @Override
                public void next() {
                    DateFormat.this.parse(builder, source, index + 1);
                }
            });
        } else if (index == this.endIndex) {
            this.strategies.get(index).parse(builder, source, new NextStrategy() {
                @Override
                public Strategy get() {
                    return null;
                }

                @Override
                public void next() {
                    if (source.getIndex() < source.getLength())
                        throw new MismatchPatternException("Does not finish at the end of string.", source.getIndex(), null);
                }
            });
        }
    }

    public DateBuilder parse(String value) {
        DateBuilder builder = new DateBuilder(this.locale, this.zone);
        StringSource source = new StringSource(value);
        this.parse(builder, source, 0);
        return builder;
    }
}
