package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;

public class ZoneOffsetConvertor implements Convertor<ZoneOffset> {
    public static final ZoneOffsetConvertor INSTANCE = new ZoneOffsetConvertor();

    @Override
    public ZoneOffset convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getZone());

        if (objective.getZone().toZoneId() instanceof ZoneOffset)
            return (ZoneOffset) objective.getZone().toZoneId();
        else
            return objective.getZone().toZoneId().getRules().getOffset(Instant.now());
    }
}
