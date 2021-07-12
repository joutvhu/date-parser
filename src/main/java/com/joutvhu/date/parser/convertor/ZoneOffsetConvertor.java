package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.TimeZone;

public class ZoneOffsetConvertor implements Convertor<ZoneOffset> {
    private static ZoneOffsetConvertor instance;

    public static synchronized ZoneOffsetConvertor getInstance() {
        if (instance == null)
            instance = new ZoneOffsetConvertor();
        return instance;
    }

    @Override
    public ZoneOffset convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getZone());
        ZoneId zoneId = objective.getZone().toZoneId();
        return zoneId instanceof ZoneOffset ? (ZoneOffset) zoneId : zoneId.getRules().getOffset(Instant.now());
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, ZoneOffset object) {
        if (object != null)
            objective.setZone(TimeZone.getTimeZone(object));
        return objective;
    }
}
