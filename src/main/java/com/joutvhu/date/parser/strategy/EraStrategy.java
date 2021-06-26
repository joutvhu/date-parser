package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;

import java.util.GregorianCalendar;

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
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(builder, source);
        String value = source.get(2);

        try {
            if ("AD".equalsIgnoreCase(value)) {
                chain.next();
                builder.set(ERA, GregorianCalendar.AD);
                backup.commit();
            } else if ("BC".equalsIgnoreCase(value)) {
                chain.next();
                builder.set(ERA, GregorianCalendar.BC);
                backup.commit();
            } else {
                throw new MismatchPatternException(
                        "The \"" + value + "\" is not the value of the Era.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        } catch (Exception e) {
            backup.restore();
            throw e;
        }
    }
}
