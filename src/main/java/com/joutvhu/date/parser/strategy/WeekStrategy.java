package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.WeekOfMonthSubscription;
import com.joutvhu.date.parser.subscription.WeekOfYearSubscription;
import com.joutvhu.date.parser.util.CommonUtil;
import com.joutvhu.date.parser.util.WeekUtil;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Objects;

/**
 * <pre>
 *  Symbol  Meaning                     Presentation      Examples
 *  ------  -------                     ------------      -------
 *   w       week-of-year                number            27
 *   W       week-of-month               number            4
 * </pre>
 *
 * @author Giao Ho
 * @version 1.0.0
 * @since 2021-07-01
 */
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
                source.get(this.ordinal ? this.length() + 1 : this.length()),
                this.length() > (this.ordinal ? 2 : 1)))
            this.tryParse(objective, chain, backup, source.get(this.ordinal ? 3 : 1), true);
    }

    @SuppressWarnings("java:S3776")
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
                            "The week '" + value + "' must be end with an ordinal indicator.",
                            backup.getBackupPosition(),
                            this.pattern);
            }
        }

        if (CommonUtil.isNumber(value)) {
            try {
                int week = Integer.parseInt(value);
                if (weekInYear) {
                    if (week < 0 || week > 54)
                        throw new MismatchPatternException(
                                MessageFormat.format("The ''{0}'' is not a week of year.", week),
                                backup.getBackupPosition(),
                                this.pattern);

                    chain.next();
                    objective.subscribe(new WeekOfYearSubscription());
                    objective.set(WEEK_OF_YEAR, week);
                } else {
                    if (week < 1 || week > 6)
                        throw new MismatchPatternException(
                                MessageFormat.format("The ''{0}'' is not a week of month.", week),
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
                        MessageFormat.format("The ''{0}'' is not a week.", value),
                        backup.getBackupPosition(),
                        this.pattern);
        }
        return false;
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Integer week;
        if (objective.getDay() != null && objective.getMonth() != null && objective.getYear() != null) {
            LocalDate localDate = LocalDate
                    .of(objective.getYear(), objective.getMonth(), objective.getDay());

            if (this.weekInYear) {
                week = WeekUtil.getWeekOfYearByDayOfYear(
                        objective.getWeekFields(),
                        localDate.getDayOfYear(),
                        localDate.getDayOfWeek().getValue());
            } else {
                week = WeekUtil.getWeekOfMonthByDayOfMonth(
                        objective.getWeekFields(),
                        localDate.getDayOfMonth(),
                        localDate.getDayOfWeek().getValue());
            }
        } else {
            if (this.weekInYear) {
                week = objective.get(WEEK_OF_YEAR);
            } else {
                week = objective.get(WEEK_OF_MONTH);
            }
        }

        Objects.requireNonNull(week, "Week of " + (this.weekInYear ? "year" : "month") + " is undefined.");
        target.append(CommonUtil.leftPad(
                String.valueOf(week),
                this.ordinal ? this.length() - 1 : this.length(),
                '0'
        ));
        if (this.ordinal)
            target.append(CommonUtil.getOrdinal(week));
        chain.next();
    }
}
