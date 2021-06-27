package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.Instant;

public class InstantConvertor implements Convertor<Instant> {
    public static final InstantConvertor INSTANCE = new InstantConvertor();

    @Override
    public Instant convert(DateBuilder builder) {
        return CalendarConvertor.INSTANCE.convert(builder).toInstant();
    }
}
