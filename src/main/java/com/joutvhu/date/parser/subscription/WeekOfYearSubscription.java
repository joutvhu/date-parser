package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.strategy.WeekStrategy;
import com.joutvhu.date.parser.strategy.WeekdayStrategy;
import com.joutvhu.date.parser.util.CommonUtil;

import java.time.LocalDate;

public class WeekOfYearSubscription implements Subscription {
    @Override
    public void changed(DateBuilder builder, String event, Object value) {
        if (DateBuilder.YEAR.equals(event) ||
                WeekdayStrategy.WEEKDAY.equals(event) ||
                WeekStrategy.WEEK_OF_YEAR.equals(event)) {
            Integer year = builder.getYear();
            Integer dayOfWeek = builder.get(WeekdayStrategy.WEEKDAY);
            Integer weekOfYear = builder.get(WeekStrategy.WEEK_OF_YEAR);

            if (year != null && dayOfWeek != null && weekOfYear != null) {
                int dayOfYear = CommonUtil.dayOfYear(weekOfYear, dayOfWeek, year, 1);
                LocalDate date = LocalDate.ofYearDay(year, dayOfYear);

                builder.set(DateBuilder.MONTH, date.getMonthValue());
                builder.set(DateBuilder.DAY, date.getDayOfMonth());
                builder.unsubscribe(WeekOfYearSubscription.class);
            }
        }
    }
}
