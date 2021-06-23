package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;

public class YearStrategy extends Strategy {
    public YearStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        boolean first = true;
        int currentIndex = source.getIndex();
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.pattern.length(), 4);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if ((first && CommonUtil.isNumber(value)) ||
                (!first && CommonUtil.isNumber(value.charAt(value.length() - 1)))) {
                try {
                    first = false;
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
                throw new MismatchException("The string \"" + value + "\" is not a year.", currentIndex, this.pattern);
            }
        }
    }
}
