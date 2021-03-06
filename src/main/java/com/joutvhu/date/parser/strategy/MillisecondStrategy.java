package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;
import java.util.Objects;
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
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(this.length(), 6);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(first, value)) {
                try {
                    chain.next();
                    int nano = Integer.parseInt(value.length() > 9 ?
                            value.substring(0, 9) :
                            CommonUtil.rightPad(value, 9, '0')
                    );
                    objective.set(ObjectiveDate.NANO, nano);
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
                        "The '" + value + "' value is not milliseconds.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Objects.requireNonNull(objective.getNano(), "Nanosecond is null.");
        String value = CommonUtil.leftPad(String.valueOf(objective.getNano()), 9, '0');
        if (value.length() > this.length())
            value = value.substring(0, this.length());
        target.append(value);
        chain.next();
    }
}
