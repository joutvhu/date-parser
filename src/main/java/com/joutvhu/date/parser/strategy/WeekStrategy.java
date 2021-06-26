package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

public class WeekStrategy extends Strategy {
    public static final String WEEK = "week";

    private final boolean weekInYear;
    private boolean ordinal;

    public WeekStrategy(char c) {
        super(c);
        this.weekInYear = c == 'w';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c, c == 'o');
    }

    @Override
    public void afterPatternSet() {
        this.ordinal = this.pattern.endsWith("o");
    }

    @Override
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(builder, source);

        if (!this.tryParse(
                builder,
                chain,
                backup,
                source.get(this.ordinal ? this.pattern.length() + 1 : this.pattern.length()),
                this.pattern.length() > (this.ordinal ? 2 : 1)))
            this.tryParse(builder, chain, backup, source.get(this.ordinal ? 3 : 1), true);
    }

    private boolean tryParse(
            DateBuilder builder,
            NextStrategy chain,
            ParseBackup backup,
            String value,
            boolean throwable
    ) {
        if (this.ordinal) {
            if (CommonUtil.hasOrdinal(value))
                value = value.substring(0, value.length() - 2);
            else {
                backup.restore();
                if (throwable)
                    throw new MismatchPatternException(
                            "The week \"" + value + "\" must be end with an ordinal indicator.",
                            backup.getBackupPosition(),
                            this.pattern);
            }
        }

        if (CommonUtil.isNumber(value)) {
            try {
                int week = Integer.parseInt(value);
                if (weekInYear) {
                    if (week < 1 || week > 53)
                        throw new MismatchPatternException(
                                "The \"" + week + "\" is not a week of year.",
                                backup.getBackupPosition(),
                                this.pattern);
                } else {
                    if (week < 1 || week > 6)
                        throw new MismatchPatternException(
                                "The \"" + week + "\" is not a week of month.",
                                backup.getBackupPosition(),
                                this.pattern);
                }

                chain.next();
                builder.set(WEEK, week);
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
                        "The \"" + value + "\" is not a week.",
                        backup.getBackupPosition(),
                        this.pattern);
        }
        return false;
    }
}
