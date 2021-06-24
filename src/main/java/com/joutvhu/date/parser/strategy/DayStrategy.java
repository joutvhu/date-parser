package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class DayStrategy extends Strategy {
    private boolean dayInYear;
    private boolean ordinal;

    public DayStrategy(char c) {
        super(c);
        this.dayInYear = c == 'D';
    }

    @Override
    public void afterPatternSet() {
        this.ordinal = this.pattern.endsWith("o");
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c, c == 'o');
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        int len = this.ordinal ? this.pattern.length() + 1 : this.pattern.length();
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(len, (this.dayInYear ? 3 : 2) + (this.ordinal ? 2 : 0));

        while (iterator.hasNext()) {
            String value = iterator.next();

            if (this.ordinal) {
                if (CommonUtil.hasOrdinal(value))
                    value = value.substring(0, value.length() - 2);
                else {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw new MismatchException(
                                "The \"" + value + "\" of day must be end with an ordinal.",
                                backup.getBackup(),
                                this.pattern);
                    }
                }
            }

            if (CommonUtil.isNumber(first, value)) {
                try {
                    this.nextStrategy(chain);

                    if (this.dayInYear) {
                        // TODO save day in year
                    } else {
                        int day = Integer.parseInt(value);
                        if (day == 0 || day > 31)
                            throw new MismatchException("The \"" + day + "\" is not a day.", backup.getBackup(), this.pattern);
                        storage.setDay(Integer.parseInt(value));
                    }
                    return;
                } catch (MismatchException e) {
                    if (iterator.hasNext())
                        continue;
                    backup.restore();
                    throw e;
                }
            } else {
                backup.restore();
                throw new MismatchException("The \"" + value + "\" is not a day.", backup.getBackup(), this.pattern);
            }
        }
    }
}
