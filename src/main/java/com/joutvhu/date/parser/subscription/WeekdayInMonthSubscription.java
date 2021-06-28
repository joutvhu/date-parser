package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.strategy.WeekdayInMonthStrategy;
import com.joutvhu.date.parser.strategy.WeekdayStrategy;
import com.joutvhu.date.parser.util.CommonUtil;

import java.time.LocalDate;

public class WeekdayInMonthSubscription implements Subscription {
    @Override
    public void changed(ObjectiveDate objective, String event, Object value) {
        if (ObjectiveDate.YEAR.equals(event) ||
            ObjectiveDate.MONTH.equals(event) ||
            WeekdayStrategy.WEEKDAY.equals(event) ||
            WeekdayInMonthStrategy.WEEKDAY_IN_MONTH.equals(event)) {
            Integer year = objective.getYear();
            Integer month = objective.getMonth();
            Integer dayOfWeek = objective.get(WeekdayStrategy.WEEKDAY);
            Integer weekdayInMonth = objective.get(WeekdayInMonthStrategy.WEEKDAY_IN_MONTH);

            if (year != null && month != null && dayOfWeek != null && weekdayInMonth != null) {
                LocalDate date = LocalDate.of(year, month, 1);
                int dayOfMonth = CommonUtil.dayOfMonth(weekdayInMonth, dayOfWeek, date.getDayOfWeek().getValue());
                // Check date is valid.
                LocalDate.of(year, month, dayOfMonth);

                objective.set(ObjectiveDate.DAY, dayOfMonth);
                objective.unsubscribe(WeekdayInMonthSubscription.class);
            }
        }
    }
}
