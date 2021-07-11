package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.AmPmStrategy;
import com.joutvhu.date.parser.strategy.HourStrategy;

import java.util.Calendar;

public class HourSubscription implements Subscription {
    @SuppressWarnings("java:S3776")
    @Override
    public void changed(ObjectiveDate objective, String event, Object value) {
        if (ObjectiveDate.HOUR.equals(event) || AmPmStrategy.AM_PM.equals(event)) {
            Integer hour = objective.getHour();
            Integer amPm = objective.get(AmPmStrategy.AM_PM);
            Integer hour12 = objective.get(HourStrategy.HOUR12);

            if (amPm != null) {
                if (hour12 != null) {
                    if (Calendar.PM == amPm && hour12 < 12)
                        hour += 12;
                    else if (hour12 == 24)
                        hour = 0;
                    else
                        hour = hour12;
                }

                if (hour != null) {
                    if ((Calendar.AM == amPm && hour > 11) || (Calendar.PM == amPm && hour < 12)) {
                        throw new ConflictDateException(
                                hour + " o'clock is not " + amPm + ".",
                                hour,
                                amPm);
                    }

                    objective.unsubscribe(HourSubscription.class);
                }

                if (hour12 != null)
                    objective.set(ObjectiveDate.HOUR, hour);
            }
        }
    }
}
