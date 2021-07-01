package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.TimeZone;

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

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, ZonedDateTime object) {
        if (object != null) {
            objective.setYear(object.getYear());
            objective.setMonth(object.getMonthValue());
            objective.setDay(object.getDayOfMonth());
            objective.setZone(TimeZone.getTimeZone(object.getZone()));
            objective.setHour(object.getHour());
            objective.setMinute(object.getMinute());
            objective.setSecond(object.getSecond());
            objective.setNano(object.getNano());
        }
        return objective;
    }
}
