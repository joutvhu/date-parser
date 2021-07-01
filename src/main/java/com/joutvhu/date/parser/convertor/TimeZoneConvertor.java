package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.util.TimeZone;

public class TimeZoneConvertor implements Convertor<TimeZone> {
    private static TimeZoneConvertor instance;

    public static synchronized TimeZoneConvertor getInstance() {
        if (instance == null)
            instance = new TimeZoneConvertor();
        return instance;
    }

    @Override
    public TimeZone convert(ObjectiveDate objective) {
        return objective.getZone();
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, TimeZone object) {
        if (object != null)
            objective.setZone(object);
        return objective;
    }
}
