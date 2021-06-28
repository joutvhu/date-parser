package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.OffsetDateTime;

public class OffsetDateTimeConvertor implements Convertor<OffsetDateTime> {
    public static final OffsetDateTimeConvertor INSTANCE = new OffsetDateTimeConvertor();

    @Override
    public OffsetDateTime convert(ObjectiveDate objective) {
        return OffsetDateTime.of(
                LocalDateTimeConvertor.INSTANCE.convert(objective),
                ZoneOffsetConvertor.INSTANCE.convert(objective)
        );
    }
}
