package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.DayStrategy;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.util.List;

public class DaySubscription implements Subscription {
    @Override
    public void changed(ObjectiveDate objective, String event, Object value) {
        if (ObjectiveDate.YEAR.equals(event) || DayStrategy.DAYS.equals(event)) {
            Integer year = objective.getYear();
            List<Pair<Integer, Integer>> days = objective.get(DayStrategy.DAYS);

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

                // Check date is valid.
                LocalDate.of(year, month, day);
                Integer oldMonth = objective.getMonth();
                if (oldMonth != null && !oldMonth.equals(month)) {
                    Integer dayOfYear = objective.get(DayStrategy.DAY_OF_YEAR);
                    throw new ConflictDateException(
                            "Conflict month (" + oldMonth + ") and day of year (" + dayOfYear + ").",
                            oldMonth,
                            dayOfYear);
                }

                objective.set(ObjectiveDate.MONTH, month);
                objective.set(ObjectiveDate.DAY, day);

                objective.unsubscribe(DaySubscription.class);
            }
        }
    }
}
