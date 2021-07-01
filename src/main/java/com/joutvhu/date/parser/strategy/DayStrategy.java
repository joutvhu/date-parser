package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.DaySubscription;
import com.joutvhu.date.parser.util.CommonUtil;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class DayStrategy extends Strategy {
    public static final String DAYS = "days";
    public static final String DAY_OF_YEAR = "day_of_year";

    private static final List<Integer> END_DAY_OF_MONTHS = Arrays
            .asList(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365);

    private final boolean dayInYear;
    private boolean ordinal;

    public DayStrategy(char c) {
        super(c);
        this.dayInYear = c == 'D';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c, c == 'o');
    }

    @Override
    public void afterPatternSet() {
        this.ordinal = this.pattern.endsWith("o");
    }

    @Override
    @SuppressWarnings("java:S3776")
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        int len = this.ordinal ? this.length() + 1 : this.length();
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(len, (this.dayInYear ? 3 : 2) + (this.ordinal ? 2 : 0));

        while (iterator.hasNext()) {
            String value = iterator.next();

            if (this.ordinal) {
                if (CommonUtil.hasOrdinal(value))
                    value = value.substring(0, value.length() - 2);
                else {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw new MismatchPatternException(
                                "The day '" + value + "' must be end with an ordinal indicator.",
                                backup.getBackupPosition(),
                                this.pattern);
                    }
                }
            }

            if (CommonUtil.isNumber(first, value)) {
                try {
                    chain.next();

                    int day = Integer.parseInt(value);
                    if (this.dayInYear) {
                        if (day == 0 || day > 366) {
                            throw new MismatchPatternException(
                                    "'" + day + "' is not a day of year.",
                                    backup.getBackupPosition(),
                                    this.pattern);
                        }
                        List<MonthDay> monthDays = getMonthAndDay(day);
                        objective.set(DAY_OF_YEAR, day);
                        if (monthDays.isEmpty()) {
                            throw new MismatchPatternException(
                                    "'" + day + "' is not a day of year.",
                                    backup.getBackupPosition(),
                                    this.pattern);
                        } else if (monthDays.size() == 1) {
                            objective.set(ObjectiveDate.MONTH, monthDays.get(0).getMonthValue());
                            objective.set(ObjectiveDate.DAY, monthDays.get(0).getDayOfMonth());
                        } else {
                            objective.subscribe(new DaySubscription());
                            objective.set(DAYS, monthDays);
                        }
                    } else {
                        if (day == 0 || day > 31) {
                            throw new MismatchPatternException(
                                    "'" + day + "' is not a day of month.",
                                    backup.getBackupPosition(),
                                    this.pattern);
                        }
                        objective.set(ObjectiveDate.DAY, day);
                    }
                    backup.commit();
                    return;
                } catch (Exception e) {
                    if (iterator.hasNext())
                        continue;
                    backup.restore();
                    throw e;
                }
            } else {
                backup.restore();
                throw new MismatchPatternException(
                        "'" + value + "' is not a day.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    @SuppressWarnings("java:S3776")
    public List<MonthDay> getMonthAndDay(int dayOfYear) {
        if (dayOfYear == 366)
            return Collections.singletonList(MonthDay.of(12, 31));
        else if (dayOfYear < 60)
            return Collections.singletonList(MonthDay.of(
                    dayOfYear > 31 ? 2 : 1,
                    dayOfYear > 31 ? dayOfYear - 31 : dayOfYear
            ));
        else if (dayOfYear == 60)
            return Arrays.asList(
                    MonthDay.of(2, 29),
                    MonthDay.of(3, 1)
            );
        else {
            for (int i = 2, len = END_DAY_OF_MONTHS.size(); i < len; i++) {
                if (END_DAY_OF_MONTHS.get(i) + 1 == dayOfYear) {
                    return Arrays.asList(
                            MonthDay.of(i, dayOfYear - END_DAY_OF_MONTHS.get(i - 1) - 1),
                            MonthDay.of(i + 1, 1)
                    );
                }
                if (END_DAY_OF_MONTHS.get(i - 1) < dayOfYear && dayOfYear <= END_DAY_OF_MONTHS.get(i)) {
                    return Arrays.asList(
                            MonthDay.of(i, dayOfYear - END_DAY_OF_MONTHS.get(i - 1) - 1),
                            MonthDay.of(i, dayOfYear - END_DAY_OF_MONTHS.get(i - 1))
                    );
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Integer day;
        if (this.dayInYear) {
            if (objective.getDay() != null && objective.getMonth() != null && objective.getYear() != null) {
                day = LocalDate
                        .of(objective.getYear(), objective.getMonth(), objective.getDay())
                        .getDayOfYear();
            } else {
                day = objective.get(DAY_OF_YEAR);
            }

            if (day == null) {
                Objects.requireNonNull(objective.getDay(), "Day is null.");
                Objects.requireNonNull(objective.getMonth(), "Month is null.");
                Objects.requireNonNull(objective.getYear(), "Year is null.");
            }
        } else {
            Objects.requireNonNull(objective.getDay(), "Day is null.");
            day = objective.getDay();
        }

        target.append(CommonUtil.leftPad(
                String.valueOf(day),
                this.ordinal ? this.length() - 1 : this.length(),
                '0'
        ));
        if (this.ordinal)
            target.append(CommonUtil.getOrdinal(day));
        chain.next();
    }
}
