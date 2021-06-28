package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.QuarterStrategy;

public class QuarterSubscription implements Subscription {
    @Override
    public void changed(ObjectiveDate objective, String event, Object value) {
        if (ObjectiveDate.MONTH.equals(event) || QuarterStrategy.QUARTER.equals(event)) {
            Integer month = objective.getMonth();
            Integer quarter = objective.get(QuarterStrategy.QUARTER);

            if (month != null && quarter != null) {
                int q = month / 3 + 1;
                if (quarter != q) {
                    throw new ConflictDateException(
                            "The month of " + month + " is not in quarter " + quarter + ".",
                            month,
                            quarter);
                } else {
                    objective.unsubscribe(QuarterSubscription.class);
                }
            }
        }
    }
}
