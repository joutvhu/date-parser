package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.strategy.WeekStrategy;
import com.joutvhu.date.parser.strategy.WeekdayStrategy;
import com.joutvhu.date.parser.util.WeekUtil;

import java.time.LocalDate;

public class WeekOfYearSubscription implements Subscription {
    @Override
    public void changed(ObjectiveDate objective, String event, Object value) {
        if (ObjectiveDate.YEAR.equals(event) ||
            WeekdayStrategy.WEEKDAY.equals(event) ||
            WeekStrategy.WEEK_OF_YEAR.equals(event)) {
            Integer year = objective.getYear();
            Integer dayOfWeek = objective.get(WeekdayStrategy.WEEKDAY);
            Integer weekOfYear = objective.get(WeekStrategy.WEEK_OF_YEAR);

            if (year != null && dayOfWeek != null && weekOfYear != null) {
                int dayOfYear = WeekUtil.getDayOfYearByWeekOfYear(
                        objective.getWeekFields(),
                        weekOfYear,
                        dayOfWeek,
                        LocalDate.of(year, 1, 1).getDayOfWeek().getValue());
                LocalDate date = LocalDate.ofYearDay(year, dayOfYear);

                objective.set(ObjectiveDate.MONTH, date.getMonthValue());
                objective.set(ObjectiveDate.DAY, date.getDayOfMonth());
                objective.unsubscribe(WeekOfYearSubscription.class);
            }
        }
    }
}
