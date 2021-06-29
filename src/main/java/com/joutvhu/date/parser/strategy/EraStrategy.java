package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;

import java.util.GregorianCalendar;
import java.util.Objects;

public class EraStrategy extends Strategy {
    public static final String ERA = "era";

    public EraStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'G', c);
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        String value = source.get(2);

        try {
            if ("AD".equalsIgnoreCase(value)) {
                chain.next();
                objective.set(ERA, GregorianCalendar.AD);
                backup.commit();
            } else if ("BC".equalsIgnoreCase(value)) {
                chain.next();
                objective.set(ERA, GregorianCalendar.BC);
                backup.commit();
            } else {
                throw new MismatchPatternException(
                        "The '" + value + "' is not the value of the Era.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        } catch (Exception e) {
            backup.restore();
            throw e;
        }
    }

    @Override
    public void afterCompletion(ObjectiveDate objective) {
        Integer era = objective.get(ERA);
        Integer year = objective.getYear();
        if (year != null && (era == GregorianCalendar.AD || era == GregorianCalendar.BC)) {
            year = Math.abs(year);
            if (era == GregorianCalendar.BC)
                year = -year;
            objective.setYear(year);
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Objects.requireNonNull(objective.getYear());
        target.append(objective.getYear() < 0 ? "BC" : "AD");
        chain.next();
    }
}
