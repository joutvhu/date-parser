package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.CenturyStrategy;
import com.joutvhu.date.parser.strategy.YearStrategy;

import java.text.MessageFormat;

public class CenturySubscription implements Subscription {
    @Override
    public void changed(DateBuilder builder, String event, Object value) {
        if (DateBuilder.YEAR.equals(event) || CenturyStrategy.CENTURY.equals(event)) {
            Integer year = builder.getYear();
            Integer century = builder.get(CenturyStrategy.CENTURY);

            if (year != null && century != null) {
                if (year < 100 && Boolean.TRUE.equals(builder.get(YearStrategy.YEAR2))) {
                    year += century * 100;
                    builder.unsubscribe(CenturySubscription.class);
                    builder.set(DateBuilder.YEAR, year);
                } else {
                    int c = year / 100;
                    if (century != c) {
                        String message = MessageFormat.format("The year {0} is not the {1} century.", year, century);
                        throw new ConflictDateException(message, century, c);
                    }
                }
            }
        }
    }
}
