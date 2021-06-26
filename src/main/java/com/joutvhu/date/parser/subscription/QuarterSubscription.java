package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.QuarterStrategy;

public class QuarterSubscription implements Subscription {
    @Override
    public void changed(DateBuilder builder, String event, Object value) {
        if (DateBuilder.MONTH.equals(event) || QuarterStrategy.QUARTER.equals(event)) {
            Integer month = builder.getMonth();
            Integer quarter = builder.get(QuarterStrategy.QUARTER);

            if (month != null && quarter != null) {
                int q = month / 3 + 1;
                if (quarter != q) {
                    throw new ConflictDateException(
                            "The month of " + month + " is not in quarter " + quarter + ".",
                            month,
                            quarter);
                } else {
                    builder.unsubscribe(QuarterSubscription.class);
                }
            }
        }
    }
}
