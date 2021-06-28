package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.util.TimeZone;

public class TimeZoneConvertor implements Convertor<TimeZone> {
    public static final TimeZoneConvertor INSTANCE = new TimeZoneConvertor();

    @Override
    public TimeZone convert(ObjectiveDate objective) {
        return objective.getZone();
    }
}
