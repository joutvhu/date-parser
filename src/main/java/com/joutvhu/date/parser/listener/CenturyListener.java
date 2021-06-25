package com.joutvhu.date.parser.listener;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.DateListener;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.CenturyStrategy;

import java.text.MessageFormat;

public class CenturyListener implements DateListener {
    @Override
    public void changed(DateBuilder builder, String event, Object value) {
        if (DateBuilder.YEAR.equals(event) || CenturyStrategy.CENTURY.equals(event)) {
            Integer year = builder.getYear();
            Integer century = builder.get(CenturyStrategy.CENTURY);

            if (year != null && century != null) {
                if (year < 100) {
                    year += century * 100;
                    builder.setYear(year);
                } else {
                    int c = year / 100;
                    if (century != c) {
                        String message = MessageFormat.format("Conflict century {} and year {}.", century, year);
                        throw new ConflictDateException(message, century, c);
                    }
                }
            }
        }
    }
}
