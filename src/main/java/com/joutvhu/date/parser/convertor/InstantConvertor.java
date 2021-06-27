package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.Instant;

public class InstantConvertor implements Convertor<Instant> {
    public static final InstantConvertor INSTANCE = new InstantConvertor();

    @Override
    public Instant convert(DateBuilder builder) {
        Instant instant = CalendarConvertor.INSTANCE.convert(builder).toInstant();
        int oldNano = instant.getNano();
        if (builder.getNano() != null && oldNano != builder.getNano())
            instant = instant.minusNanos(oldNano).plusNanos(builder.getNano());
        return instant;
    }
}
