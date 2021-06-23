package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class YearStrategy extends Strategy {
    int length;

    public YearStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void afterPatternSet() {
        this.length = this.pattern.length();
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.length, 4);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(first, value)) {
                try {
                    this.nextStrategy(chain);
                    storage.setYear(Integer.parseInt(value));
                    return;
                } catch (MismatchException e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            } else {
                backup.restore();
                throw new MismatchException("The \"" + value + "\" is not a year.", backup.getBackup(), this.pattern);
            }
        }
    }
}
