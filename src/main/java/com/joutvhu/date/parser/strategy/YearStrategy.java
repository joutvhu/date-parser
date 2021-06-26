package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class YearStrategy extends Strategy {
    public static final String YEAR2 = "year2";

    private int length;

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
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.length, 4);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(first, value)) {
                try {
                    chain.next();
                    builder.set(YEAR2, this.length < 3 && value.length() < 3);
                    builder.set(DateBuilder.YEAR, Integer.parseInt(value));
                    return;
                } catch (Exception e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            } else {
                backup.restore();
                throw new MismatchPatternException("The \"" + value + "\" is not a year.", backup.getBackup(), this.pattern);
            }
        }
    }
}
