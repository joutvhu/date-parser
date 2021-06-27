package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.ZonedDateTime;
import java.util.Objects;

public class ZonedDateTimeConvertor implements Convertor<ZonedDateTime> {
    public static final ZonedDateTimeConvertor INSTANCE = new ZonedDateTimeConvertor();

    @Override
    public ZonedDateTime convert(DateBuilder builder) {
        Objects.requireNonNull(builder.getZone());

        return ZonedDateTime.of(
                LocalDateTimeConvertor.INSTANCE.convert(builder),
                builder.getZone().toZoneId()
        );
    }
}
