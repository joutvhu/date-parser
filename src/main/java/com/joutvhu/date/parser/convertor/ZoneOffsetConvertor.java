package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;

public class ZoneOffsetConvertor implements Convertor<ZoneOffset> {
    public static final ZoneOffsetConvertor INSTANCE = new ZoneOffsetConvertor();

    @Override
    public ZoneOffset convert(DateBuilder builder) {
        Objects.requireNonNull(builder.getZone());

        if (builder.getZone().toZoneId() instanceof ZoneOffset)
            return (ZoneOffset) builder.getZone().toZoneId();
        else
            return builder.getZone().toZoneId().getRules().getOffset(Instant.now());
    }
}
