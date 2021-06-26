package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.HourSubscription;

import java.util.Calendar;

public class AmPmStrategy extends Strategy {
    public static final String AM_PM = "am/pm";

    private final boolean upperCase;

    public AmPmStrategy(char c) {
        super(c);
        this.upperCase = c == 'A';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(builder, source);
        String value = source.get(2);

        try {
            if ("am".equalsIgnoreCase(value)) {
                chain.next();
                builder.subscribe(new HourSubscription());
                builder.set(AM_PM, Calendar.AM);
                backup.commit();
            } else if ("pm".equalsIgnoreCase(value)) {
                chain.next();
                builder.subscribe(new HourSubscription());
                builder.set(AM_PM, Calendar.PM);
                backup.commit();
            } else {
                throw new MismatchPatternException(
                        "The \"" + value + "\" value must be \"AM\" or \"PM\".",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        } catch (Exception e) {
            backup.restore();
            throw e;
        }
    }
}
