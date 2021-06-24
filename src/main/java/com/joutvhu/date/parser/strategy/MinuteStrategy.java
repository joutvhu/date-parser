package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;

public class MinuteStrategy extends Strategy {
    public MinuteStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'm', c);
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.pattern.length(), 2);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(value)) {
                try {
                    this.nextStrategy(chain);
                } catch (MismatchException e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }

                int minute = Integer.parseInt(value);
                if (minute < 0 || minute > 59)
                    throw new MismatchException("The \"" + value + "\" is not a minute.", backup.getBackup(), this.pattern);
                storage.setMinute(minute);
                return;
            } else {
                backup.restore();
                throw new MismatchException("The \"" + value + "\" is not a minute.", backup.getBackup(), this.pattern);
            }
        }
    }
}
