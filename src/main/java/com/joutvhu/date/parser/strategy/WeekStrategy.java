package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.WeekOfMonthSubscription;
import com.joutvhu.date.parser.subscription.WeekOfYearSubscription;
import com.joutvhu.date.parser.util.CommonUtil;

public class WeekStrategy extends Strategy {
    public static final String WEEK_OF_YEAR = "week_of_year";
    public static final String WEEK_OF_MONTH = "week_of_month";

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
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);

        if (!this.tryParse(
                objective,
                chain,
                backup,
                source.get(this.ordinal ? this.pattern.length() + 1 : this.pattern.length()),
                this.pattern.length() > (this.ordinal ? 2 : 1)))
            this.tryParse(objective, chain, backup, source.get(this.ordinal ? 3 : 1), true);
    }

    private boolean tryParse(
            ObjectiveDate objective,
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
                    if (week < 1 || week > 54)
                        throw new MismatchPatternException(
                                "The \"" + week + "\" is not a week of year.",
                                backup.getBackupPosition(),
                                this.pattern);

                    chain.next();
                    objective.subscribe(new WeekOfYearSubscription());
                    objective.set(WEEK_OF_YEAR, week);
                } else {
                    if (week < 1 || week > 6)
                        throw new MismatchPatternException(
                                "The \"" + week + "\" is not a week of month.",
                                backup.getBackupPosition(),
                                this.pattern);

                    chain.next();
                    objective.subscribe(new WeekOfMonthSubscription());
                    objective.set(WEEK_OF_MONTH, week);
                }

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
