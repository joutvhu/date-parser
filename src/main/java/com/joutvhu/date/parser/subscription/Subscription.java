package com.joutvhu.date.parser.subscription;

import com.joutvhu.date.parser.domain.DateBuilder;

public interface Subscription {
    void changed(DateBuilder builder, String event, Object value);
}
