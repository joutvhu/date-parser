package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;
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
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        if (number)
            this.parseNumber(storage, source, chain);
        else
            this.parseString(storage, source, chain);
    }

    private void parseNumber(DateStorage storage, StringSource source, NextStrategy chain) {
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
                    throw new MismatchException("The \"" + value + "\" of month must be end with an ordinal.", backup.getBackup(), this.pattern);
                }
            }

            if (CommonUtil.isNumber(first, value)) {
                try {
                    this.nextStrategy(chain);
                    storage.setMonth(Integer.parseInt(value));
                    return;
                } catch (MismatchException e) {
                    if (iterator.hasNext())
                        continue;
                    backup.restore();
                    throw e;
                }
            } else {
                backup.restore();
                throw new MismatchException("The \"" + value + "\" is not a month.", backup.getBackup(), this.pattern);
            }
        }
    }

    private void parseString(DateStorage storage, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(3);

        if (this.pattern.length() == 3) {
            int index = CommonUtil.indexIgnoreCaseOf(value, SHORT_MONTHS);
            this.tryParse(storage, chain, backup, index + 1, true);
        } else {
            for (int i = 0; i < 6; i++) {
                value += source.get(1);
                int index = CommonUtil.indexIgnoreCaseOf(value, LONG_MONTHS);
                this.tryParse(storage, chain, backup, index + 1, i == 5);
            }
        }

        backup.restore();
        throw new MismatchException("The \"" + value + "\" is not a month.", backup.getBackup(), this.pattern);
    }

    private void tryParse(
            DateStorage storage,
            NextStrategy chain,
            StringSource.PositionBackup backup,
            int value,
            boolean throwable
    ) {
        if (value > 0) {
            try {
                this.nextStrategy(chain);
                storage.setMonth(value);
            } catch (MismatchException e) {
                if (throwable) {
                    backup.restore();
                    throw e;
                }
            }
        }
    }
}
