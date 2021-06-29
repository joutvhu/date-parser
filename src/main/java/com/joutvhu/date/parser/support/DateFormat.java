package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.strategy.NextStrategy;
import com.joutvhu.date.parser.strategy.Strategy;
import com.joutvhu.date.parser.strategy.StrategyFactory;

import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormat {
    private Locale locale;
    private TimeZone zone;
    private WeekFields weekFields;
    private List<Strategy> strategies;
    private int endIndex;

    public DateFormat(String pattern) {
        this(pattern, null);
    }

    public DateFormat(String pattern, StrategyFactory strategyFactory) {
        this.strategies = new DatePatternSplitter(pattern, strategyFactory).getStrategyChain();
        this.endIndex = this.strategies.size() - 1;
    }

    public DateFormat with(Locale locale) {
        this.locale = locale;
        return this;
    }

    public DateFormat with(TimeZone zone) {
        this.zone = zone;
        return this;
    }

    public DateFormat with(WeekFields weekFields) {
        this.weekFields = weekFields;
        return this;
    }

    private void parse(ObjectiveDate objective, StringSource source, int index) {
        if (index < this.endIndex) {
            this.strategies.get(index).parse(objective, source, new NextStrategy() {
                @Override
                public Strategy get() {
                    return DateFormat.this.strategies.get(index + 1);
                }

                @Override
                public void next() {
                    DateFormat.this.parse(objective, source, index + 1);
                }
            });
        } else if (index == this.endIndex) {
            this.strategies.get(index).parse(objective, source, new NextStrategy() {
                @Override
                public Strategy get() {
                    return null;
                }

                @Override
                public void next() {
                    if (source.getIndex() < source.getLength())
                        throw new MismatchPatternException("Does not finish at the end of string.", source
                                .getIndex(), null);
                }
            });
        }
    }

    public ObjectiveDate parse(String value) {
        ObjectiveDate objective = new ObjectiveDate(this.locale, this.zone, this.weekFields);
        StringSource source = new StringSource(value);
        this.parse(objective, source, 0);
        this.strategies.forEach(strategy -> strategy.afterCompletion(objective));
        return objective;
    }

    private void format(ObjectiveDate objective, StringBuilder target, int index) {
        if (index < this.endIndex) {
            this.strategies.get(index).format(objective, target, new NextStrategy() {
                @Override
                public Strategy get() {
                    return DateFormat.this.strategies.get(index + 1);
                }

                @Override
                public void next() {
                    DateFormat.this.format(objective, target, index + 1);
                }
            });
        } else if (index == this.endIndex) {
            this.strategies.get(index).format(objective, target, new NextStrategy() {
                @Override
                public Strategy get() {
                    return null;
                }

                @Override
                public void next() {
                    // Do nothing
                }
            });
        }
    }

    public String format(ObjectiveDate objective) {
        StringBuilder target = new StringBuilder();
        this.format(objective, target, 0);
        return target.toString();
    }
}
