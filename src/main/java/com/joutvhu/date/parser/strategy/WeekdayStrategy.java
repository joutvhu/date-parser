package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Arrays;
import java.util.List;

public class WeekdayStrategy extends Strategy {
    public static final String WEEKDAY = "weekday";

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
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        if (text)
            this.parseString(builder, source, chain);
        else
            this.parseNumber(builder, source, chain);
    }

    private void parseNumber(DateBuilder builder, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(builder, source);

        if (!this.tryParse(
                builder,
                chain,
                backup,
                source.get(this.pattern.length()),
                this.pattern.length() > 1))
            this.tryParse(builder, chain, backup, source.get(1), true);
    }

    private boolean tryParse(
            DateBuilder builder,
            NextStrategy chain,
            ParseBackup backup,
            String value,
            boolean throwable
    ) {
        if (CommonUtil.isNumber(value)) {
            try {
                int weekday = Integer.parseInt(value);
                if (weekday < 1 || weekday > 6)
                    throw new MismatchPatternException(
                            "The \"" + weekday + "\" is not a day of week.",
                            backup.getBackupPosition(),
                            this.pattern);

                chain.next();
                builder.set(WEEKDAY, weekday);
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
                        "The \"" + value + "\" is not a day of week.",
                        backup.getBackupPosition(),
                        this.pattern);
        }
        return false;
    }

    private void parseString(DateBuilder builder, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(builder, source);
        String value = source.get(3);

        if (this.pattern.length() < 4) {
            int index = CommonUtil.indexIgnoreCaseOf(value, SHORT_WEEKDAYS);
            this.tryParse(builder, chain, backup, index + 1, true);
        } else {
            for (int i = 0; i < 6; i++) {
                value += source.get(1);
                int index = CommonUtil.indexIgnoreCaseOf(value, LONG_WEEKDAYS);
                if (this.tryParse(builder, chain, backup, index + 1, i == 5))
                    return;
            }
        }

        backup.restore();
        throw new MismatchPatternException(
                "The \"" + value + "\" is not a day of week.",
                backup.getBackupPosition(),
                this.pattern);
    }

    private boolean tryParse(
            DateBuilder builder,
            NextStrategy chain,
            ParseBackup backup,
            int value,
            boolean throwable
    ) {
        // 1 (Monday) to 7 (Sunday)
        if (value > 0 && value < 8) {
            try {
                chain.next();
                builder.set(WEEKDAY, value);
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
