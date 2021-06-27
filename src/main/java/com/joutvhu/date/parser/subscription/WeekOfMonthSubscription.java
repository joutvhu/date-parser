package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.strategy.WeekStrategy;
import com.joutvhu.date.parser.strategy.WeekdayStrategy;
import com.joutvhu.date.parser.util.CommonUtil;

import java.time.LocalDate;

public class WeekOfMonthSubscription implements Subscription {
    @Override
    public void changed(DateBuilder builder, String event, Object value) {
        if (DateBuilder.YEAR.equals(event) ||
                DateBuilder.MONTH.equals(event) ||
                WeekdayStrategy.WEEKDAY.equals(event) ||
                WeekStrategy.WEEK_OF_MONTH.equals(event)) {
            Integer year = builder.getYear();
            Integer month = builder.getMonth();
            Integer dayOfWeek = builder.get(WeekdayStrategy.WEEKDAY);
            Integer weekOfMonth = builder.get(WeekStrategy.WEEK_OF_MONTH);

            if (year != null && month != null && dayOfWeek != null && weekOfMonth != null) {
                LocalDate date = LocalDate.of(year, month, 1);
                int dayOfMonth = CommonUtil.dayOfYear(weekOfMonth, dayOfWeek, date.getDayOfWeek().getValue());
                // Check date is valid.
                LocalDate.of(year, month, dayOfMonth);

                builder.set(DateBuilder.DAY, dayOfMonth);
                builder.unsubscribe(WeekOfMonthSubscription.class);
            }
        }
    }
}
