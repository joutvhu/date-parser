package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;
import com.joutvhu.date.parser.util.NameUtil;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *  Symbol  Meaning                     Presentation      Examples
 *  ------  -------                     ------------      -------
 *   c       stand-alone-day-of-week     text              Sunday; Sun
 *   e       day-number-of-week          number            1; 7
 *   E       day-name-in-week            text              Tuesday; Tue
 * </pre>
 *
 * @author Giao Ho
 * @version 1.0.0
 * @since 2021-07-01
 */
public class WeekdayStrategy extends Strategy {
    public static final String WEEKDAY = "weekday";

    private static final String NOT_DAY_OF_WEEK_MESSAGE = "The ''{0}'' is not a day of week.";

    private final boolean numeric;
    private final boolean standAlone;

    public WeekdayStrategy(char c) {
        super(c);
        this.numeric = c == 'e';
        this.standAlone = c == 'c';
    }

    @Override
    public boolean add(char c) {
        if (this.standAlone)
            return false;
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        if (numeric)
            this.parseNumber(objective, source, chain);
        else
            this.parseString(objective, source, chain);
    }

    private void parseNumber(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);

        if (!this.tryParse(
                objective,
                chain,
                backup,
                source.get(this.length()),
                this.length() > 1))
            this.tryParse(objective, chain, backup, source.get(1), true);
    }

    private boolean tryParse(
            ObjectiveDate objective,
            NextStrategy chain,
            ParseBackup backup,
            String value,
            boolean throwable
    ) {
        if (CommonUtil.isNumber(value)) {
            try {
                int weekday = Integer.parseInt(value);
                if (weekday < 1 || weekday > 7)
                    throw new MismatchPatternException(
                            MessageFormat.format(NOT_DAY_OF_WEEK_MESSAGE, weekday),
                            backup.getBackupPosition(),
                            this.pattern);

                chain.next();
                objective.set(WEEKDAY, weekday);
                backup.commit();
                return true;
            } catch (Exception e) {
                backup.restore();
                if (throwable)
                    throw e;
            }
        } else {
            backup.restore();
            if (throwable)
                throw new MismatchPatternException(
                        MessageFormat.format(NOT_DAY_OF_WEEK_MESSAGE, value),
                        backup.getBackupPosition(),
                        this.pattern);
        }
        return false;
    }

    @SuppressWarnings("java:S1643")
    private void parseString(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(1);

        TextStyle[] styles = this.standAlone ?
                new TextStyle[]{TextStyle.SHORT_STANDALONE, TextStyle.FULL_STANDALONE} :
                new TextStyle[]{TextStyle.SHORT, TextStyle.FULL};

        Map.Entry<String, DayOfWeek> entry = NameUtil.findName(
                iterator,
                DayOfWeek.values(),
                new Locale[]{objective.getLocale(), Locale.ROOT},
                styles,
                (value, style, locale) -> value.getDisplayName(style, locale),
                value -> {
                    try {
                        chain.next();
                        objective.set(WEEKDAY, value.getValue());
                        backup.commit();
                        return true;
                    } catch (Exception e) {
                        if (!iterator.hasNext()) {
                            backup.restore();
                            throw e;
                        }
                    }
                    return false;
                }
        );

        if (entry.getValue() == null) {
            backup.restore();
            throw new MismatchPatternException(
                    MessageFormat.format(NOT_DAY_OF_WEEK_MESSAGE, entry.getKey()),
                    backup.getBackupPosition(),
                    this.pattern);
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        DayOfWeek dayOfWeek;
        if (objective.getYear() != null && objective.getMonth() != null && objective.getDay() != null) {
            dayOfWeek = LocalDate
                    .of(objective.getYear(), objective.getMonth(), objective.getDay())
                    .getDayOfWeek();
        } else {
            Integer w = objective.get(WEEKDAY);
            Objects.requireNonNull(w);
            dayOfWeek = DayOfWeek.of(w);
        }

        Objects.requireNonNull(dayOfWeek, "Day of week is undefined.");

        if (this.numeric) {
            target.append(CommonUtil.leftPad(
                    String.valueOf(dayOfWeek.getValue()),
                    this.length(),
                    '0'
            ));
        } else {
            Objects.requireNonNull(objective.getLocale());
            if (this.length() < 4) {
                target.append(dayOfWeek.getDisplayName(
                        this.standAlone ? TextStyle.SHORT_STANDALONE : TextStyle.SHORT,
                        objective.getLocale()
                ));
            } else {
                target.append(dayOfWeek.getDisplayName(
                        this.standAlone ? TextStyle.FULL_STANDALONE : TextStyle.FULL,
                        objective.getLocale()
                ));
            }
        }

        chain.next();
    }
}
