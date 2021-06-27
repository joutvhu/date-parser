package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.LocalTime;
import java.util.Objects;

public class LocalTimeConvertor implements Convertor<LocalTime> {
    public static final LocalTimeConvertor INSTANCE = new LocalTimeConvertor();

    @Override
    public LocalTime convert(DateBuilder builder) {
        Objects.requireNonNull(builder.getHour());
        Objects.requireNonNull(builder.getMinute());

        LocalTime localTime = LocalTime.of(builder.getHour(), builder.getMinute());
        if (builder.getSecond() != null)
            localTime = localTime.withSecond(builder.getSecond());
        if (builder.getNano() != null)
            localTime = localTime.withNano(builder.getNano());

        return localTime;
    }
}
