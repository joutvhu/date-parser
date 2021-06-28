package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.CenturySubscription;
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
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(this.pattern.length(), 2);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(value)) {
                try {
                    int century = Integer.parseInt(value);
                    chain.next();
                    objective.subscribe(new CenturySubscription());
                    objective.set(CENTURY, century);
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
                        "\"" + value + "\" is not the century.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }
}
