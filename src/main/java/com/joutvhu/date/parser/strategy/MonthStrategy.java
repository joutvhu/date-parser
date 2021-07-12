package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;
import com.joutvhu.date.parser.util.NameUtil;

import java.text.MessageFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MonthStrategy extends Strategy {
    private static final String NOT_MONTH_MESSAGE = "The ''{0}'' is not a month.";

    private boolean numeric;
    private boolean ordinal;
    private final boolean standAlone;

    public MonthStrategy(char c) {
        super(c);
        this.standAlone = c == 'L';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c, c == 'o');
    }

    @Override
    public void afterPatternSet() {
        this.ordinal = this.pattern.endsWith("o");
        this.numeric = this.ordinal || this.length() <= 2;
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        if (numeric)
            this.parseNumber(objective, source, chain);
        else
            this.parseString(objective, source, chain);
    }

    @SuppressWarnings({"java:S3776", "java:S135"})
    private void parseNumber(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        AtomicBoolean first = new AtomicBoolean(true);
        int len = this.ordinal ? this.length() + 1 : this.length();
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(len, this.ordinal ? 4 : 2);

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
                            "The month '" + value + "' must be end with an ordinal indicator.",
                            backup.getBackupPosition(),
                            this.pattern);
                }
            }

            if (CommonUtil.isNumber(first, value)) {
                try {
                    int month = Integer.parseInt(value);
                    if (month < 1 || month > 12) {
                        throw new MismatchPatternException(
                                MessageFormat.format(NOT_MONTH_MESSAGE, month),
                                backup.getBackupPosition(),
                                this.pattern);
                    }

                    chain.next();
                    objective.set(ObjectiveDate.MONTH, month);
                    backup.commit();
                    return;
                } catch (Exception e) {
                    if (iterator.hasNext())
                        continue;
                    backup.restore();
                    throw e;
                }
            } else {
                backup.restore();
                throw new MismatchPatternException(
                        MessageFormat.format(NOT_MONTH_MESSAGE, value),
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }
    }

    @SuppressWarnings("java:S1643")
    private void parseString(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);

        TextStyle[] styles = this.standAlone ?
                new TextStyle[]{TextStyle.SHORT_STANDALONE, TextStyle.FULL_STANDALONE} :
                new TextStyle[]{TextStyle.SHORT, TextStyle.FULL};

        Iterator<String> iterator = source.iterator(1);
        Map.Entry<String, Month> entry = NameUtil.findName(
                iterator,
                Month.values(),
                new Locale[]{objective.getLocale(), Locale.ROOT},
                styles,
                (value, style, locale) -> value.getDisplayName(style, locale),
                value -> {
                    try {
                        chain.next();
                        objective.set(ObjectiveDate.MONTH, value.getValue());
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
                    MessageFormat.format(NOT_MONTH_MESSAGE, entry.getKey()),
                    backup.getBackupPosition(),
                    this.pattern);
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Integer month = objective.getMonth();
        Objects.requireNonNull(month, "Month is null.");
        ChronoField.MONTH_OF_YEAR.checkValidIntValue(month);

        if (this.numeric) {
            target.append(CommonUtil.leftPad(
                    String.valueOf(month),
                    this.ordinal ? this.length() - 1 : this.length(),
                    '0'
            ));
            if (this.ordinal)
                target.append(CommonUtil.getOrdinal(month));
        } else {
            if (this.length() == 3) {
                target.append(Month.of(month).getDisplayName(
                        this.standAlone ? TextStyle.SHORT_STANDALONE : TextStyle.SHORT,
                        objective.getLocale()
                ));
            } else {
                target.append(Month.of(month).getDisplayName(
                        this.standAlone ? TextStyle.FULL_STANDALONE : TextStyle.FULL,
                        objective.getLocale()
                ));
            }
        }
        chain.next();
    }
}
