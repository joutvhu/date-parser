package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.strategy.WeekStrategy;
import com.joutvhu.date.parser.strategy.WeekdayStrategy;
import com.joutvhu.date.parser.util.CommonUtil;
import com.joutvhu.date.parser.util.WeekUtil;

import java.time.LocalDate;

public class WeekOfMonthSubscription implements Subscription {
    @Override
    public void changed(ObjectiveDate objective, String event, Object value) {
        if (ObjectiveDate.YEAR.equals(event) ||
            ObjectiveDate.MONTH.equals(event) ||
            WeekdayStrategy.WEEKDAY.equals(event) ||
            WeekStrategy.WEEK_OF_MONTH.equals(event)) {
            Integer year = objective.getYear();
            Integer month = objective.getMonth();
            Integer dayOfWeek = objective.get(WeekdayStrategy.WEEKDAY);
            Integer weekOfMonth = objective.get(WeekStrategy.WEEK_OF_MONTH);

            if (year != null && month != null && dayOfWeek != null && weekOfMonth != null) {
                int dayOfMonth = WeekUtil.getDayOfMonthByWeekOfMonth(
                        objective.getWeekFields(),
                        weekOfMonth,
                        dayOfWeek,
                        LocalDate.of(year, month, 1).getDayOfWeek().getValue());
                // Check date is valid.
                CommonUtil.checkValidDate(year, month, dayOfMonth);

                objective.set(ObjectiveDate.DAY, dayOfMonth);
                objective.unsubscribe(WeekOfMonthSubscription.class);
            }
        }
    }
}
