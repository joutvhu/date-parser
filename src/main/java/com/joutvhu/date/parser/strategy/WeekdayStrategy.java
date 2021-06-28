package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class WeekdayStrategy extends Strategy {
    public static final String WEEKDAY = "weekday";

    private static final String NOT_DAY_OF_WEEK_MESSAGE = "The '{0}' is not a day of week.";

    private static final List<String> SHORT_WEEKDAYS = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
    private static final List<String> LONG_WEEKDAYS = Arrays
            .asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    private final boolean text;

    public WeekdayStrategy(char c) {
        super(c);
        this.text = c == 'E';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        if (text)
            this.parseString(objective, source, chain);
        else
            this.parseNumber(objective, source, chain);
    }

    private void parseNumber(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);

        if (!this.tryParse(
                objective,
                chain,
                backup,
                source.get(this.pattern.length()),
                this.pattern.length() > 1))
            this.tryParse(objective, chain, backup, source.get(1), true);
    }

    private boolean tryParse(
            ObjectiveDate objective,
            NextStrategy chain,
            ParseBackup backup,
            String value,
            boolean throwable
    ) {
        if (CommonUtil.isNumber(value)) {
            try {
                int weekday = Integer.parseInt(value);
                if (weekday < 1 || weekday > 7)
                    throw new MismatchPatternException(
                            MessageFormat.format(NOT_DAY_OF_WEEK_MESSAGE, weekday),
                            backup.getBackupPosition(),
                            this.pattern);

                chain.next();
                objective.set(WEEKDAY, weekday);
                backup.commit();
                return true;
            } catch (Exception e) {
                backup.restore();
                if (throwable)
                    throw e;
            }
        } else {
            backup.restore();
            if (throwable)
                throw new MismatchPatternException(
                        MessageFormat.format(NOT_DAY_OF_WEEK_MESSAGE, value),
                        backup.getBackupPosition(),
                        this.pattern);
        }
        return false;
    }

    @SuppressWarnings("java:S1643")
    private void parseString(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        String value = source.get(3);

        if (this.pattern.length() < 4) {
            int index = CommonUtil.indexIgnoreCaseOf(value, SHORT_WEEKDAYS);
            if (this.tryParse(objective, chain, backup, index + 1, true))
                return;
        } else {
            for (int i = 0; i < 6; i++) {
                value += source.get(1);
                int index = CommonUtil.indexIgnoreCaseOf(value, LONG_WEEKDAYS);
                if (this.tryParse(objective, chain, backup, index + 1, i == 5))
                    return;
            }
        }

        backup.restore();
        throw new MismatchPatternException(
                MessageFormat.format(NOT_DAY_OF_WEEK_MESSAGE, value),
                backup.getBackupPosition(),
                this.pattern);
    }

    private boolean tryParse(
            ObjectiveDate objective,
            NextStrategy chain,
            ParseBackup backup,
            int value,
            boolean throwable
    ) {
        // 1 (Monday) to 7 (Sunday)
        if (value > 0 && value < 8) {
            try {
                chain.next();
                objective.set(WEEKDAY, value);
                backup.commit();
                return true;
            } catch (Exception e) {
                if (throwable) {
                    backup.restore();
                    throw e;
                }
            }
        }
        return false;
    }
}
