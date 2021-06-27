package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.util.TimeZone;

public class TimeZoneConvertor implements Convertor<TimeZone> {
    public static final TimeZoneConvertor INSTANCE = new TimeZoneConvertor();

    @Override
    public TimeZone convert(DateBuilder builder) {
        return builder.getZone();
    }
}
