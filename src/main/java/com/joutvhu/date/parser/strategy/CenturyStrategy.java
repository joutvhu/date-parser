package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.CenturySubscription;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;
import java.util.Objects;

public class CenturyStrategy extends Strategy {
    public static final String CENTURY = "century";

    private boolean ordinal;

    public CenturyStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'C', c, c == 'o');
    }

    @Override
    public void afterPatternSet() {
        this.ordinal = this.pattern.endsWith("o");
    }

    @SuppressWarnings("java:S3776")
    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = this.ordinal ?
                source.iterator(this.length() + 1, 4) :
                source.iterator(this.length(), 2);

        while (iterator.hasNext()) {
            String value = iterator.next();

            if (this.ordinal) {
                if (CommonUtil.hasOrdinal(value))
                    value = value.substring(0, value.length() - 2);
                else {
                    if (iterator.hasNext())
                        continue;
                    backup.restore();
                    throw new MismatchPatternException(
                            "The century '" + value + "' must be end with an ordinal indicator.",
                            backup.getBackupPosition(),
                            this.pattern);
                }
            }

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
                        "'" + value + "' is not the century.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Integer century;
        if (objective.getYear() != null) {
            century = Math.abs(objective.getYear()) / 100 + 1;
        } else {
            century = objective.get(CENTURY);
        }

        Objects.requireNonNull(century, "Year is null.");
        target.append(CommonUtil.leftPad(
                String.valueOf(century),
                this.ordinal ? this.length() - 1 : this.length(),
                '0'
        ));
        if (this.ordinal)
            target.append(CommonUtil.getOrdinal(century));
        chain.next();
    }
}
