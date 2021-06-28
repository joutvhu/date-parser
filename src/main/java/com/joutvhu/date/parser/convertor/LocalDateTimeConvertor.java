package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.LocalDateTime;

public class LocalDateTimeConvertor implements Convertor<LocalDateTime> {
    public static final LocalDateTimeConvertor INSTANCE = new LocalDateTimeConvertor();

    @Override
    public LocalDateTime convert(ObjectiveDate objective) {
        return LocalDateTime.of(
                LocalDateConvertor.INSTANCE.convert(objective),
                LocalTimeConvertor.INSTANCE.convert(objective)
        );
    }
}
