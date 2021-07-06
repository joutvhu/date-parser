package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.ZoneUtil;

import java.util.Iterator;
import java.util.Objects;
import java.util.TimeZone;

public class ZoneStrategy extends Strategy {
    public ZoneStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(this.length());

        while (iterator.hasNext()) {
            String value = iterator.next();

            if (value.startsWith(" ")) {
                backup.restore();
                throw new MismatchPatternException(
                        "The time zone can't start with a space.",
                        backup.getBackupPosition(),
                        this.pattern);
            }

            TimeZone timeZone = ZoneUtil.getTimeZone(value, objective.getLocale());
            if (timeZone != null) {
                try {
                    chain.next();
                    objective.set(ObjectiveDate.ZONE, timeZone);
                    backup.commit();
                    return;
                } catch (Exception e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            }
        }

        backup.restore();
        throw new MismatchPatternException(
                "The time zone is invalid.",
                backup.getBackupPosition(),
                this.pattern);
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Objects.requireNonNull(objective.getZone(), "Zone is null.");
        target.append(objective.getZone().getID());
        chain.next();
    }
}
