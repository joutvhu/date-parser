package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MonthStrategy extends Strategy {
    private static final String NOT_MONTH_MESSAGE = "The '{0}' is not a month.";

    private static final List<String> SHORT_MONTHS = Arrays
            .asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    private static final List<String> LONG_MONTHS = Arrays
            .asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    private boolean number;
    private boolean ordinal;

    public MonthStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c, c == 'o');
    }

    @Override
    public void afterPatternSet() {
        this.ordinal = this.pattern.endsWith("o");
        this.number = this.ordinal || this.pattern.length() <= 2;
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        if (number)
            this.parseNumber(objective, source, chain);
        else
            this.parseString(objective, source, chain);
    }

    @SuppressWarnings({"java:S3776", "java:S135"})
    private void parseNumber(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        int len = this.ordinal ? this.pattern.length() + 1 : this.pattern.length();
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(len, this.ordinal ? 4 : 2);

        while (iterator.hasNext()) {
            String value = iterator.next();

            if (this.ordinal) {
                if (CommonUtil.hasOrdinal(value))
                    value = value.substring(0, value.length() - 2);
                else {
                    if (iterator.hasNext())
                        continue;
                    backup.restore();
                    throw new MismatchPatternException(
                            "The month '" + value + "' must be end with an ordinal indicator.",
                            backup.getBackupPosition(),
                            this.pattern);
                }
            }

            if (CommonUtil.isNumber(first, value)) {
                try {
                    int month = Integer.parseInt(value);
                    if (month < 1 || month > 12) {
                        throw new MismatchPatternException(
                                MessageFormat.format(NOT_MONTH_MESSAGE, month),
                                backup.getBackupPosition(),
                                this.pattern);
                    }

                    chain.next();
                    objective.set(ObjectiveDate.MONTH, month);
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
                        MessageFormat.format(NOT_MONTH_MESSAGE, value),
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    @SuppressWarnings("java:S1643")
    private void parseString(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        String value = source.get(3);

        if (this.pattern.length() == 3) {
            int index = CommonUtil.indexIgnoreCaseOf(value, SHORT_MONTHS);
            this.tryParse(objective, chain, backup, index + 1, true);
        } else {
            for (int i = 0; i < 6; i++) {
                value += source.get(1);
                int index = CommonUtil.indexIgnoreCaseOf(value, LONG_MONTHS);
                if (this.tryParse(objective, chain, backup, index + 1, i == 5))
                    return;
            }
        }

        backup.restore();
        throw new MismatchPatternException(
                MessageFormat.format(NOT_MONTH_MESSAGE, value),
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
        if (value > 0 && value < 13) {
            try {
                chain.next();
                objective.set(ObjectiveDate.MONTH, value);
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

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {

    }
}
