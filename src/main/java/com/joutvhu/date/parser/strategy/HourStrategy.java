package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;

public class HourStrategy extends Strategy {
    private final boolean hour24;
    private final boolean startFrom0;

    public HourStrategy(char c) {
        super(c);
        this.hour24 = c == 'H' || c == 'k';
        this.startFrom0 = c == 'H' || c == 'K';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.pattern.length(), 2);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(value)) {
                try {
                    this.nextStrategy(chain);

                    int hour = Integer.parseInt(value);
                    if (hour24) {
                        if (!startFrom0 && hour == 24)
                            hour = 0;
                        if (hour < 0 || hour > 23)
                            throw new MismatchPatternException("The \"" + value + "\" is not a hour.", backup.getBackup(), this.pattern);
                    } else {
                        if (!startFrom0 && hour == 12)
                            hour = 0;
                        String am_pm = builder.get(AmPmStrategy.AM_PM);
                        if (hour < 12 && AmPmStrategy.PM.equals(am_pm))
                            hour += 12;
                        if (hour < 0 || hour > 24 || AmPmStrategy.AM.equals(am_pm) && hour > 11)
                            throw new MismatchPatternException("The \"" + value + "\" is not a hour.", backup.getBackup(), this.pattern);
                        if (!startFrom0 && hour == 24)
                            hour = 0;
                    }
                    builder.set(DateBuilder.HOUR, hour);
                    return;
                } catch (Exception e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            } else {
                backup.restore();
                throw new MismatchPatternException("The \"" + value + "\" is not a hour.", backup.getBackup(), this.pattern);
            }
        }
    }
}
