package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.listener.CenturySubscription;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;

public class CenturyStrategy extends Strategy {
    public static final String CENTURY = "century";

    public CenturyStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'C', c);
    }

    @Override
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.pattern.length(), 2);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(value)) {
                try {
                    int century = Integer.parseInt(value);
                    chain.next();
                    builder.subscribe(new CenturySubscription());
                    builder.set(CENTURY, century);
                    return;
                } catch (Exception e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            } else {
                backup.restore();
                throw new MismatchPatternException("The \"" + value + "\" is not a century.", backup.getBackup(), this.pattern);
            }
        }
    }
}
