package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.ParseBackup;
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
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        ParseBackup backup = ParseBackup.backup(builder, source);
        Iterator<String> iterator = source.iterator(this.pattern.length(), 6);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(first, value)) {
                try {
                    chain.next();
                    int nano = Integer.parseInt(CommonUtil.rightPad(value, 9, '0'));
                    builder.set(DateBuilder.NANO, nano);
                    backup.commit();
                    return;
                } catch (Exception e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            } else {
                backup.restore();
                throw new MismatchPatternException(
                        "The \"" + value + "\" value is not milliseconds.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }
}
