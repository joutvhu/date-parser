package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
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
        StringSource.PositionBackup backup = source.backup();

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
            StringSource.PositionBackup backup,
            String value,
            boolean throwable
    ) {
        if (this.ordinal) {
            if (CommonUtil.hasOrdinal(value))
                value = value.substring(0, value.length() - 2);
            else {
                backup.restore();
                if (throwable)
                    throw new MismatchPatternException("The \"" + value + "\" of week must be end with an ordinal.", backup.getBackup(), this.pattern);
            }
        }

        if (CommonUtil.isNumber(value)) {
            try {
                int week = Integer.parseInt(value);
                if (weekInYear) {
                    if (week < 1 || week > 53)
                        throw new MismatchPatternException("The \"" + week + "\" is not a week of year.", backup.getBackup(), this.pattern);
                } else {
                    if (week < 1 || week > 6)
                        throw new MismatchPatternException("The \"" + week + "\" is not a week of month.", backup.getBackup(), this.pattern);
                }

                this.nextStrategy(chain);
                builder.put(WEEK, week);
                return true;
            } catch (Exception e) {
                backup.restore();
                if (throwable)
                    throw e;
            }
        } else {
            backup.restore();
            if (throwable)
                throw new MismatchPatternException("The \"" + value + "\" is not a week.", backup.getBackup(), this.pattern);
        }
        return false;
    }
}
