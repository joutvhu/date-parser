package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Arrays;
import java.util.List;

public class WeekdayStrategy extends Strategy {
    public static final String WEEKDAY = "weekday";

    private static final List<String> SHORT_WEEKDAYS = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
    private static final List<String> LONG_WEEKDAYS = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    private boolean text;

    public WeekdayStrategy(char c) {
        super(c);
        this.text = c == 'E';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        if (text)
            this.parseString(storage, source, chain);
        else
            this.parseNumber(storage, source, chain);
    }

    private void parseNumber(DateStorage storage, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        if (!this.tryParse(
                storage,
                chain,
                backup,
                source.get(this.pattern.length()),
                this.pattern.length() > 1))
            this.tryParse(storage, chain, backup, source.get(1), true);
    }

    private boolean tryParse(
            DateStorage storage,
            NextStrategy chain,
            StringSource.PositionBackup backup,
            String value,
            boolean throwable
    ) {
        if (CommonUtil.isNumber(value)) {
            try {
                int weekday = Integer.parseInt(value);
                if (weekday < 1 || weekday > 6)
                    throw new MismatchException("The \"" + weekday + "\" is not a day of week.", backup.getBackup(), this.pattern);

                this.nextStrategy(chain);
                storage.put(WEEKDAY, weekday);
                return true;
            } catch (MismatchException e) {
                backup.restore();
                if (throwable)
                    throw e;
            }
        } else {
            backup.restore();
            if (throwable)
                throw new MismatchException("The \"" + value + "\" is not a day of week.", backup.getBackup(), this.pattern);
        }
        return false;
    }

    private void parseString(DateStorage storage, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(3);

        if (this.pattern.length() < 4) {
            int index = CommonUtil.indexIgnoreCaseOf(value, SHORT_WEEKDAYS);
            this.tryParse(storage, chain, backup, index + 1, true);
        } else {
            for (int i = 0; i < 6; i++) {
                value += source.get(1);
                int index = CommonUtil.indexIgnoreCaseOf(value, LONG_WEEKDAYS);
                this.tryParse(storage, chain, backup, index + 1, i == 5);
            }
        }

        backup.restore();
        throw new MismatchException("The \"" + value + "\" is not a day of week.", backup.getBackup(), this.pattern);
    }

    private void tryParse(
            DateStorage storage,
            NextStrategy chain,
            StringSource.PositionBackup backup,
            int value,
            boolean throwable
    ) {
        // 1 (Monday) to 7 (Sunday)
        if (value > 0 && value < 8) {
            try {
                this.nextStrategy(chain);
                storage.put(WEEKDAY, value);
            } catch (MismatchException e) {
                if (throwable) {
                    backup.restore();
                    throw e;
                }
            }
        }
    }
}
