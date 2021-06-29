package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
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
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(this.length, 4);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(first, value)) {
                try {
                    chain.next();
                    objective.set(YEAR2, this.length < 3 && value.length() < 3);
                    objective.set(ObjectiveDate.YEAR, Integer.parseInt(value));
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
                        "The '" + value + "' is not a year.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {

    }
}
