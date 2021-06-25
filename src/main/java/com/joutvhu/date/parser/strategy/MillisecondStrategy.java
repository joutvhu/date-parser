package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;
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
                this.nextStrategy(chain);
                int nano = Integer.parseInt(CommonUtil.rightPad(value, 9, '0'));
                storage.setNano(nano);
                return;
            } else {
                backup.restore();
                throw new MismatchException("The \"" + value + "\" is not a millisecond.", backup.getBackup(), this.pattern);
            }
        }
    }
}
