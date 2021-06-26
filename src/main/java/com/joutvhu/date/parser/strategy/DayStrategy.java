package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.DaySubscription;
import com.joutvhu.date.parser.util.CommonUtil;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DayStrategy extends Strategy {
    public static final String DAYS = "days";
    public static final String DAY_OF_YEAR = "day_of_year";

    private static final List<Integer> END_DAY_OF_MONTHS = Arrays
            .asList(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365);
    private static final List<Integer> CONFLICT_DAYS = Arrays.asList(60, 91, 121, 152, 182, 213, 244, 274, 305, 335);

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
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        int len = this.ordinal ? this.pattern.length() + 1 : this.pattern.length();
        ParseBackup backup = ParseBackup.backup(builder, source);
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
                                "The day \"" + value + "\" must be end with an ordinal indicator.",
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
                                    "\"" + day + "\" is not a day of year.",
                                    backup.getBackupPosition(),
                                    this.pattern);
                        }
                        List<Pair<Integer, Integer>> days = getMonthAndDay(day);
                        builder.set(DAY_OF_YEAR, day);
                        if (days.isEmpty()) {
                            throw new MismatchPatternException(
                                    "\"" + day + "\" is not a day of year.",
                                    backup.getBackupPosition(),
                                    this.pattern);
                        } else if (days.size() == 1) {
                            builder.set(DateBuilder.MONTH, days.get(0).getKey());
                            builder.set(DateBuilder.DAY, days.get(0).getValue());
                        } else {
                            builder.subscribe(new DaySubscription());
                            builder.set(DAYS, days);
                        }
                    } else {
                        if (day == 0 || day > 31) {
                            throw new MismatchPatternException(
                                    "\"" + day + "\" is not a day of month.",
                                    backup.getBackupPosition(),
                                    this.pattern);
                        }
                        builder.set(DateBuilder.DAY, day);
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
                        "\"" + value + "\" is not a day.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    public List<Pair<Integer, Integer>> getMonthAndDay(int dayOfYear) {
        if (dayOfYear == 366)
            return Collections.singletonList(new Pair<>(12, 31));
        else if (dayOfYear < 60)
            return Collections.singletonList(new Pair<>(
                    dayOfYear > 31 ? 2 : 1,
                    dayOfYear > 31 ? dayOfYear - 31 : dayOfYear
            ));
        else if (dayOfYear == 60)
            return Arrays.asList(
                    new Pair<>(2, 29),
                    new Pair<>(3, 1)
            );
        else {
            for (int i = 2, len = END_DAY_OF_MONTHS.size(); i < len; i++) {
                if (END_DAY_OF_MONTHS.get(i) + 1 == dayOfYear) {
                    return Arrays.asList(
                            new Pair<>(i, dayOfYear - END_DAY_OF_MONTHS.get(i - 1) - 1),
                            new Pair<>(i + 1, 1)
                    );
                }
                if (END_DAY_OF_MONTHS.get(i - 1) < dayOfYear && dayOfYear <= END_DAY_OF_MONTHS.get(i)) {
                    return Arrays.asList(
                            new Pair<>(i, dayOfYear - END_DAY_OF_MONTHS.get(i - 1) - 1),
                            new Pair<>(i, dayOfYear - END_DAY_OF_MONTHS.get(i - 1))
                    );
                }
            }
        }
        return Collections.emptyList();
    }
}
