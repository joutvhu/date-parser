package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.DayStrategy;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.chrono.IsoChronology;
import java.util.List;

public class DaySubscription implements Subscription {
    @Override
    public void changed(ObjectiveDate objective, String event, Object value) {
        if (ObjectiveDate.YEAR.equals(event) || DayStrategy.DAYS.equals(event)) {
            Integer year = objective.getYear();
            List<MonthDay> days = objective.get(DayStrategy.DAYS);

            if (year != null && days != null && days.size() == 2) {
                MonthDay monthDay = IsoChronology.INSTANCE.isLeapYear(year) ? days.get(0) : days.get(1);
                // Check date is valid.
                LocalDate.of(year, monthDay.getMonth(), monthDay.getDayOfMonth());
                Integer oldMonth = objective.getMonth();
                if (oldMonth != null && !oldMonth.equals(monthDay.getMonthValue())) {
                    Integer dayOfYear = objective.get(DayStrategy.DAY_OF_YEAR);
                    throw new ConflictDateException(
                            "Conflict month (" + oldMonth + ") and day of year (" + dayOfYear + ").",
                            oldMonth,
                            dayOfYear);
                }

                objective.set(ObjectiveDate.MONTH, monthDay.getMonthValue());
                objective.set(ObjectiveDate.DAY, monthDay.getDayOfMonth());

                objective.unsubscribe(DaySubscription.class);
            }
        }
    }
}
