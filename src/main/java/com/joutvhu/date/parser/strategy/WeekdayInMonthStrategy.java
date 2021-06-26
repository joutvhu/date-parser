package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.ParseBackup;
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
        ParseBackup backup = ParseBackup.backup(builder, source);
        Character value = source.character();

        if ('0' < value && value < '6') {
            source.next();
            try {
                chain.next();
                int weekdayInMonth = value - '0';
                builder.set(WEEKDAY_IN_MONTH, weekdayInMonth);
                backup.commit();
            } catch (Exception e) {
                backup.restore();
                throw e;
            }
        } else {
            throw new MismatchPatternException(
                    "The \"" + value + "\" is not a week in month.",
                    backup.getBackupPosition(),
                    this.pattern);
        }
    }
}
