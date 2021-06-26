package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.AmPmStrategy;
import com.joutvhu.date.parser.strategy.HourStrategy;

import java.util.Calendar;

public class HourSubscription implements Subscription {
    @Override
    public void changed(DateBuilder builder, String event, Object value) {
        if (DateBuilder.HOUR.equals(event) || AmPmStrategy.AM_PM.equals(event)) {
            Integer hour = builder.getHour();
            Integer amPm = builder.get(AmPmStrategy.AM_PM);
            Integer hour12 = builder.get(HourStrategy.HOUR12);

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

                    builder.unsubscribe(HourSubscription.class);
                }

                if (hour12 != null)
                    builder.set(DateBuilder.HOUR, hour);
            }
        }
    }
}
