package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
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
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.pattern.length(), 2);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(value)) {
                try {
                    int minute = Integer.parseInt(value);
                    if (minute < 0 || minute > 59)
                        throw new MismatchPatternException("The \"" + value + "\" is not a minute.", backup.getBackup(), this.pattern);

                    chain.next();
                    builder.setMinute(minute);
                    return;
                } catch (Exception e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            } else {
                backup.restore();
                throw new MismatchPatternException("The \"" + value + "\" is not a minute.", backup.getBackup(), this.pattern);
            }
        }
    }
}
