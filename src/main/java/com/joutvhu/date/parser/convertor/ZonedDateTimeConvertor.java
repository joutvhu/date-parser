package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.ZonedDateTime;
import java.util.Objects;

public class ZonedDateTimeConvertor implements Convertor<ZonedDateTime> {
    private static ZonedDateTimeConvertor instance;

    public static synchronized ZonedDateTimeConvertor getInstance() {
        if (instance == null)
            instance = new ZonedDateTimeConvertor();
        return instance;
    }

    @Override
    public ZonedDateTime convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getZone());

        return ZonedDateTime.of(
                LocalDateTimeConvertor.getInstance().convert(objective),
                objective.getZone().toZoneId()
        );
    }
}
