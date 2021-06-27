package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.LocalDateTime;

public class LocalDateTimeConvertor implements Convertor<LocalDateTime> {
    public static final LocalDateTimeConvertor INSTANCE = new LocalDateTimeConvertor();

    @Override
    public LocalDateTime convert(DateBuilder builder) {
        return LocalDateTime.of(
                LocalDateConvertor.INSTANCE.convert(builder),
                LocalTimeConvertor.INSTANCE.convert(builder)
        );
    }
}
