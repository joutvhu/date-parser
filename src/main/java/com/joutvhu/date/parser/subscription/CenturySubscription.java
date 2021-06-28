package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.exception.ConflictDateException;
import com.joutvhu.date.parser.strategy.CenturyStrategy;
import com.joutvhu.date.parser.strategy.YearStrategy;

public class CenturySubscription implements Subscription {
    @Override
    public void changed(ObjectiveDate objective, String event, Object value) {
        if (ObjectiveDate.YEAR.equals(event) || CenturyStrategy.CENTURY.equals(event)) {
            Integer year = objective.getYear();
            Integer century = objective.get(CenturyStrategy.CENTURY);

            if (year != null && century != null) {
                if (year < 100 && Boolean.TRUE.equals(objective.get(YearStrategy.YEAR2))) {
                    year += (century - 1) * 100;
                    objective.unsubscribe(CenturySubscription.class);
                    objective.set(ObjectiveDate.YEAR, year);
                } else {
                    int c = year / 100;
                    if (century - 1 != c) {
                        throw new ConflictDateException(
                                "The year " + year + " is not the " + century + " century.",
                                century,
                                c);
                    }
                }
            }
        }
    }
}
