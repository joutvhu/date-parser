package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.strategy.WeekdayInMonthStrategy;
import com.joutvhu.date.parser.strategy.WeekdayStrategy;
import com.joutvhu.date.parser.util.CommonUtil;

import java.time.LocalDate;

public class WeekdayInMonthSubscription implements Subscription {
    @Override
    public void changed(DateBuilder builder, String event, Object value) {
        if (DateBuilder.YEAR.equals(event) ||
                DateBuilder.MONTH.equals(event) ||
                WeekdayStrategy.WEEKDAY.equals(event) ||
                WeekdayInMonthStrategy.WEEKDAY_IN_MONTH.equals(event)) {
            Integer year = builder.getYear();
            Integer month = builder.getMonth();
            Integer dayOfWeek = builder.get(WeekdayStrategy.WEEKDAY);
            Integer weekdayInMonth = builder.get(WeekdayInMonthStrategy.WEEKDAY_IN_MONTH);

            if (year != null && month != null && dayOfWeek != null && weekdayInMonth != null) {
                LocalDate date = LocalDate.of(year, month, 1);
                int dayOfMonth = CommonUtil.dayOfMonth(weekdayInMonth, dayOfWeek, date.getDayOfWeek().getValue());
                // Check date is valid.
                LocalDate.of(year, month, dayOfMonth);

                builder.set(DateBuilder.DAY, dayOfMonth);
                builder.unsubscribe(WeekdayInMonthSubscription.class);
            }
        }
    }
}
