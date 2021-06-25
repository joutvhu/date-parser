package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;
import com.joutvhu.date.parser.util.ZoneUtil;

import java.util.Iterator;
import java.util.TimeZone;

public class ZoneStrategy extends Strategy {
    public ZoneStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.pattern.length());

        while (iterator.hasNext()) {
            String value = iterator.next();
            TimeZone timeZone = ZoneUtil.getTimeZone(value);
            if (timeZone != null) {
                try {
                    this.nextStrategy(chain);
                    storage.setZone(timeZone);
                    return;
                } catch (MismatchException e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            }
        }

        backup.restore();
        throw new MismatchException("The time zone is invalid.", backup.getBackup(), this.pattern);
    }
}
