package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.OffsetTime;

public class OffsetTimeConvertor implements Convertor<OffsetTime> {
    public static final OffsetTimeConvertor INSTANCE = new OffsetTimeConvertor();

    @Override
    public OffsetTime convert(DateBuilder builder) {
        return OffsetTime.of(
                LocalTimeConvertor.INSTANCE.convert(builder),
                ZoneOffsetConvertor.INSTANCE.convert(builder)
        );
    }
}
