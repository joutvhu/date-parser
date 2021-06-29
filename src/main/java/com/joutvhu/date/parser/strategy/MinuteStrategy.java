package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;
import java.util.Objects;

public class MinuteStrategy extends Strategy {
    public MinuteStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'm', c);
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(this.length(), 2);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(value)) {
                try {
                    int minute = Integer.parseInt(value);
                    if (minute < 0 || minute > 59) {
                        throw new MismatchPatternException(
                                "The '" + minute + "' value is not minute.",
                                backup.getBackupPosition(),
                                this.pattern);
                    }

                    chain.next();
                    objective.set(ObjectiveDate.MINUTE, minute);
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
                        "The '" + value + "' value is not minute.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Objects.requireNonNull(objective.getMinute());
        target.append(CommonUtil.leftPad(
                String.valueOf(objective.getMinute()),
                this.length(),
                '0'
        ));
        chain.next();
    }
}
