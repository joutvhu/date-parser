package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.AmPmStrategy;
import com.joutvhu.date.parser.strategy.HourStrategy;

import java.text.MessageFormat;

public class HourSubscription implements Subscription {
    @Override
    public void changed(DateBuilder builder, String event, Object value) {
        if (DateBuilder.HOUR.equals(event) || AmPmStrategy.AM_PM.equals(event)) {
            Integer hour = builder.getHour();
            String amPm = builder.get(AmPmStrategy.AM_PM);
            Integer hour12 = builder.get(HourStrategy.HOUR12);

            if (amPm != null) {
                if (hour12 != null) {
                    if (AmPmStrategy.PM.equals(amPm) && hour12 < 12)
                        hour += 12;
                    else if (hour12 == 24)
                        hour = 0;
                    else
                        hour = hour12;
                }

                if (hour != null) {
                    if ((AmPmStrategy.AM.equals(amPm) && hour > 11) ||
                            (AmPmStrategy.PM.equals(amPm) && hour < 12)) {
                        String message = MessageFormat.format("{0} o'clock is not {1}.", hour, amPm);
                        throw new ConflictDateException(message, hour, amPm);
                    }

                    builder.unsubscribe(HourSubscription.class);
                }

                if (hour12 != null)
                    builder.set(DateBuilder.HOUR, hour);
            }
        }
    }
}
