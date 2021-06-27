package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.OffsetDateTime;

public class OffsetDateTimeConvertor implements Convertor<OffsetDateTime> {
    public static final OffsetDateTimeConvertor INSTANCE = new OffsetDateTimeConvertor();

    @Override
    public OffsetDateTime convert(DateBuilder builder) {
        return OffsetDateTime.of(
                LocalDateTimeConvertor.INSTANCE.convert(builder),
                ZoneOffsetConvertor.INSTANCE.convert(builder)
        );
    }
}
