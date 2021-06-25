package com.joutvhu.date.parser.domain;

public interface DateListener {
    void changed(DateBuilder builder, String event, Object value);
}
