package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.text.MessageFormat;
import java.util.Iterator;

public class HourStrategy extends Strategy {
    public static final String HOUR12 = "hour12";

    private static final String NOT_HOUR_MESSAGE = "The value '{0}' is not a hour.";

    private final boolean hour24;
    private final boolean startFrom0;

    public HourStrategy(char c) {
        super(c);
        this.hour24 = c == 'H' || c == 'k';
        this.startFrom0 = c == 'H' || c == 'K';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    @SuppressWarnings("java:S3776")
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(this.length(), 2);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(value)) {
                try {
                    chain.next();

                    int hour = Integer.parseInt(value);
                    if (hour24) {
                        if (hour == 24)
                            hour = 0;
                        if (hour < 0 || hour > 23) {
                            throw new MismatchPatternException(
                                    MessageFormat.format(NOT_HOUR_MESSAGE, hour),
                                    backup.getBackupPosition(),
                                    this.pattern);
                        }
                    } else {
                        if (hour < 0 || hour > 24) {
                            throw new MismatchPatternException(
                                    MessageFormat.format(NOT_HOUR_MESSAGE, hour),
                                    backup.getBackupPosition(),
                                    this.pattern);
                        }
                        if (!this.startFrom0 && hour == 12)
                            hour = 0;
                        objective.set(HOUR12, hour);
                        if (hour == 24)
                            hour = 0;
                    }
                    objective.set(ObjectiveDate.HOUR, hour);
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
                        MessageFormat.format(NOT_HOUR_MESSAGE, value),
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {

    }
}
