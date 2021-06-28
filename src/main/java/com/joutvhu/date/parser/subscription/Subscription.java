package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.ObjectiveDate;

public interface Subscription {
    void changed(ObjectiveDate objective, String event, Object value);
}
