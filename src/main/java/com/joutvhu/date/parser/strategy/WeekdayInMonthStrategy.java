package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;

public class WeekdayInMonthStrategy extends Strategy {
    public static final String WEEKDAY_IN_MONTH = "weekday_in_month";

    public WeekdayInMonthStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return false;
    }

    @Override
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        Character value = source.character();

        if ('0' < value && value < '6') {
            source.next();
            try {
                this.nextStrategy(chain);
                int weekdayInMonth = value - '0';
                builder.put(WEEKDAY_IN_MONTH, weekdayInMonth);
            } catch (Exception e) {
                backup.restore();
                throw e;
            }
        } else {
            throw new MismatchPatternException("The \"" + value + "\" is not a week in month.", backup.getBackup(), this.pattern);
        }
    }
}
