package com.joutvhu.date.parser.domain;

public interface DateSubscription {
    void changed(DateBuilder builder, String event, Object value);
}
