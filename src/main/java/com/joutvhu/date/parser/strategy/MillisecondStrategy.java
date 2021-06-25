package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class MillisecondStrategy extends Strategy {
    public MillisecondStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'S', c);
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        StringSource.PositionBackup backup = source.backup();
        Iterator<String> iterator = source.iterator(this.pattern.length(), 6);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(first, value)) {
                try {
                    this.nextStrategy(chain);
                    int nano = Integer.parseInt(CommonUtil.rightPad(value, 9, '0'));
                    storage.setNano(nano);
                    return;
                } catch (Exception e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            } else {
                backup.restore();
                throw new MismatchPatternException("The \"" + value + "\" is not a millisecond.", backup.getBackup(), this.pattern);
            }
        }
    }
}
