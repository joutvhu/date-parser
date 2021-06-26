package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;

public class SecondStrategy extends Strategy {
    public SecondStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 's', c);
    }

    @Override
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(builder, source);
        Iterator<String> iterator = source.iterator(this.pattern.length(), 2);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(value)) {
                try {
                    int second = Integer.parseInt(value);
                    if (second < 0 || second > 59) {
                        throw new MismatchPatternException(
                                "The \"" + second + "\" is not a second.",
                                backup.getBackupPosition(),
                                this.pattern);
                    }

                    chain.next();
                    builder.set(DateBuilder.SECOND, second);
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
                        "The \"" + value + "\" is not a second.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }
}
