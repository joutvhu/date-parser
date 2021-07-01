package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.WeekdayInMonthSubscription;
import com.joutvhu.date.parser.util.WeekUtil;

import java.time.LocalDate;
import java.util.Objects;

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
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        Character value = source.character();

        if ('0' < value && value < '6') {
            source.next();
            try {
                chain.next();
                int weekdayInMonth = value - '0';
                objective.subscribe(new WeekdayInMonthSubscription());
                objective.set(WEEKDAY_IN_MONTH, weekdayInMonth);
                backup.commit();
            } catch (Exception e) {
                backup.restore();
                throw e;
            }
        } else {
            throw new MismatchPatternException(
                    "The '" + value + "' is not a week in month.",
                    backup.getBackupPosition(),
                    this.pattern);
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Integer week;
        if (objective.getDay() != null && objective.getMonth() != null && objective.getYear() != null) {
            week = WeekUtil.getWeekdayInMonthByDayOfMonth(
                    LocalDate.of(objective.getYear(), objective.getMonth(), objective.getDay())
                            .getDayOfMonth());
        } else {
            week = objective.get(WEEKDAY_IN_MONTH);
        }

        Objects.requireNonNull(week, "Week in month is undefined.");
        target.append(week);
        chain.next();
    }
}
