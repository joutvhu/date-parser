package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;

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

        if (objective.getZone().toZoneId() instanceof ZoneOffset)
            return (ZoneOffset) objective.getZone().toZoneId();
        else
            return objective.getZone().toZoneId().getRules().getOffset(Instant.now());
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, ZoneOffset object) {
        return objective;
    }
}
