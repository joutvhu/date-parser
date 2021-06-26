package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
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
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.pattern.length());

        while (iterator.hasNext()) {
            String value = iterator.next();
            TimeZone timeZone = ZoneUtil.getTimeZone(value);
            if (timeZone != null) {
                try {
                    chain.next();
                    builder.setZone(timeZone);
                    return;
                } catch (Exception e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            }
        }

        backup.restore();
        throw new MismatchPatternException("The time zone is invalid.", backup.getBackup(), this.pattern);
    }
}
