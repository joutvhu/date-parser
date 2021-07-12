package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <pre>
 *  Symbol  Meaning                     Presentation      Examples
 *  ------  -------                     ------------      -------
 *   n       nano-of-second              number            987654321
 *   N       nano-of-day                 number            1234000000
 * </pre>
 *
 * @author Giao Ho
 * @version 1.0.0
 * @since 2021-07-09
 */
public class NanoStrategy extends Strategy {
    private boolean nanoOfDay;

    public NanoStrategy(char c) {
        super(c);
        this.nanoOfDay = c == 'N';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(this.length(), this.nanoOfDay ? 14 : 9);

        while (iterator.hasNext()) {
            String value = iterator.next();
            if (CommonUtil.isNumber(first, value)) {
                try {
                    chain.next();
                    long nano = Long.parseLong(value);
                    if (this.nanoOfDay) {
                        int hour = (int) (nano / 3600000000000L);
                        objective.set(ObjectiveDate.HOUR, hour);
                        nano = nano % 3600000000000L;
                        int minute = (int) (nano / 60000000000L);
                        objective.set(ObjectiveDate.MINUTE, minute);
                        nano = nano % 60000000000L;
                        int second = (int) (nano / 1000000000L);
                        objective.set(ObjectiveDate.SECOND, second);
                        nano = nano % 1000000000L;
                    }
                    objective.set(ObjectiveDate.NANO, (int) nano);
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
                        "The '" + value + "' value is not nanosecond.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Objects.requireNonNull(objective.getNano(), "Nanosecond is null.");
        long nano = objective.getNano();
        if (this.nanoOfDay) {
            Objects.requireNonNull(objective.getHour(), "Hour is null.");
            Objects.requireNonNull(objective.getMinute(), "Minute is null.");
            Objects.requireNonNull(objective.getSecond(), "Second is null.");

            nano += objective.getHour() * 3600000000000L;
            nano += objective.getMinute() * 60000000000L;
            nano += objective.getSecond() * 1000000000L;
        }
        target.append(CommonUtil.leftPad(String.valueOf(nano), this.length(), '0'));
        chain.next();
    }
}
