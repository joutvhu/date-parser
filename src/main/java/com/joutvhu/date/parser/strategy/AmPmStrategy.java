package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.listener.HourSubscription;

import java.text.MessageFormat;

public class AmPmStrategy extends Strategy {
    public static final String AM = "am";
    public static final String PM = "pm";
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
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(2);

        try {
            if ("am".equalsIgnoreCase(value)) {
                chain.next();
                builder.subscribe(new HourSubscription());
                builder.set(AM_PM, AM);
            } else if ("pm".equalsIgnoreCase(value)) {
                chain.next();
                builder.subscribe(new HourSubscription());
                builder.set(AM_PM, PM);
            } else {
                String message = MessageFormat.format("The \"{0}\" value must be \"AM\" or \"PM\".", value);
                throw new MismatchPatternException(message, backup.getBackup(), this.pattern);
            }
        } catch (Exception e) {
            backup.restore();
            throw e;
        }
    }
}
