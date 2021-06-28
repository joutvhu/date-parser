package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.ZonedDateTime;
import java.util.Objects;

public class ZonedDateTimeConvertor implements Convertor<ZonedDateTime> {
    public static final ZonedDateTimeConvertor INSTANCE = new ZonedDateTimeConvertor();

    @Override
    public ZonedDateTime convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getZone());

        return ZonedDateTime.of(
                LocalDateTimeConvertor.INSTANCE.convert(objective),
                objective.getZone().toZoneId()
        );
    }
}
