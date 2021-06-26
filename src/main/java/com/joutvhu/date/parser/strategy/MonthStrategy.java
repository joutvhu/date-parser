package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MonthStrategy extends Strategy {
    private static final List<String> SHORT_MONTHS = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    private static final List<String> LONG_MONTHS = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

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
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        if (number)
            this.parseNumber(builder, source, chain);
        else
            this.parseString(builder, source, chain);
    }

    private void parseNumber(DateBuilder builder, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        int len = this.ordinal ? this.pattern.length() + 1 : this.pattern.length();
        StringSource.PositionBackup backup = source.backup();
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
                    throw new MismatchPatternException("The \"" + value + "\" of month must be end with an ordinal.", backup.getBackup(), this.pattern);
                }
            }

            if (CommonUtil.isNumber(first, value)) {
                try {
                    int month = Integer.parseInt(value);
                    if (month < 1 || month > 12)
                        throw new MismatchPatternException("The \"" + month + "\" is not a month.", backup.getBackup(), this.pattern);

                    chain.next();
                    builder.setMonth(month);
                    return;
                } catch (Exception e) {
                    if (iterator.hasNext())
                        continue;
                    backup.restore();
                    throw e;
                }
            } else {
                backup.restore();
                throw new MismatchPatternException("The \"" + value + "\" is not a month.", backup.getBackup(), this.pattern);
            }
        }
    }

    private void parseString(DateBuilder builder, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(3);

        if (this.pattern.length() == 3) {
            int index = CommonUtil.indexIgnoreCaseOf(value, SHORT_MONTHS);
            this.tryParse(builder, chain, backup, index + 1, true);
        } else {
            for (int i = 0; i < 6; i++) {
                value += source.get(1);
                int index = CommonUtil.indexIgnoreCaseOf(value, LONG_MONTHS);
                this.tryParse(builder, chain, backup, index + 1, i == 5);
            }
        }

        backup.restore();
        throw new MismatchPatternException("The \"" + value + "\" is not a month.", backup.getBackup(), this.pattern);
    }

    private void tryParse(
            DateBuilder builder,
            NextStrategy chain,
            StringSource.PositionBackup backup,
            int value,
            boolean throwable
    ) {
        if (value > 0 && value < 13) {
            try {
                chain.next();
                builder.setMonth(value);
            } catch (Exception e) {
                if (throwable) {
                    backup.restore();
                    throw e;
                }
            }
        }
    }
}
