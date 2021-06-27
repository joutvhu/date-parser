package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.LocalDate;
import java.util.Objects;

public class LocalDateConvertor implements Convertor<LocalDate> {
    public static final LocalDateConvertor INSTANCE = new LocalDateConvertor();

    @Override
    public LocalDate convert(DateBuilder builder) {
        Objects.requireNonNull(builder.getYear());
        Objects.requireNonNull(builder.getMonth());
        Objects.requireNonNull(builder.getDay());

        return LocalDate.of(builder.getYear(), builder.getMonth(), builder.getDay());
    }
}
