package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.HourSubscription;

import java.util.Calendar;
import java.util.Objects;

public class AmPmStrategy extends Strategy {
    public static final String AM_PM = "am/pm";

    @SuppressWarnings("java:S125")
    public AmPmStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        String value = source.get(2);

        try {
            if ("am".equalsIgnoreCase(value)) {
                chain.next();
                objective.subscribe(new HourSubscription());
                objective.set(AM_PM, Calendar.AM);
                backup.commit();
            } else if ("pm".equalsIgnoreCase(value)) {
                chain.next();
                objective.subscribe(new HourSubscription());
                objective.set(AM_PM, Calendar.PM);
                backup.commit();
            } else {
                throw new MismatchPatternException(
                        "The '" + value + "' value must be 'AM' or 'PM'.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        } catch (Exception e) {
            backup.restore();
            throw e;
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        String value = null;
        if (objective.getHour() != null) {
            value = objective.getHour() < 12 ? "AM" : "PM";
        } else {
            Integer pm = objective.get(AM_PM);
            if (pm == Calendar.AM)
                value = "AM";
            else if (pm == Calendar.PM)
                value = "PM";
        }
        Objects.requireNonNull(value, "AM or PM undefined.");

        target.append(value);
        chain.next();
    }
}
