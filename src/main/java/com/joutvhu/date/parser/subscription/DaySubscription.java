package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.DayStrategy;
import javafx.util.Pair;

import java.time.chrono.IsoChronology;
import java.util.List;

public class DaySubscription implements Subscription {
    @Override
    public void changed(DateBuilder builder, String event, Object value) {
        if (DateBuilder.YEAR.equals(event) || DayStrategy.DAYS.equals(event)) {
            Integer year = builder.getYear();
            List<Pair<Integer, Integer>> days = builder.get(DayStrategy.DAYS);

            if (year != null && days != null && days.size() == 2) {
                Integer day;
                Integer month;
                Pair<Integer, Integer> day1 = days.get(0);
                Pair<Integer, Integer> day2 = days.get(1);

                if (IsoChronology.INSTANCE.isLeapYear(year)) {
                    month = day1.getKey();
                    day = day1.getValue();
                } else {
                    month = day2.getKey();
                    day = day2.getValue();
                }

                Integer oldMonth = builder.getMonth();
                if (oldMonth != null && !oldMonth.equals(month)) {
                    Integer dayOfYear = builder.get(DayStrategy.DAY_OF_YEAR);
                    throw new ConflictDateException(
                            "Conflict month (" + oldMonth + ") and day of year (" + dayOfYear + ").",
                            oldMonth,
                            dayOfYear);
                }

                builder.set(DateBuilder.MONTH, month);
                builder.set(DateBuilder.DAY, day);

                builder.unsubscribe(DaySubscription.class);
            }
        }
    }
}
