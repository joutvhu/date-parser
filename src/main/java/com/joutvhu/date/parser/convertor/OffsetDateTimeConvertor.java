package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.OffsetDateTime;
import java.util.TimeZone;

public class OffsetDateTimeConvertor implements Convertor<OffsetDateTime> {
    private static OffsetDateTimeConvertor instance;

    public static synchronized OffsetDateTimeConvertor getInstance() {
        if (instance == null)
            instance = new OffsetDateTimeConvertor();
        return instance;
    }

    @Override
    public OffsetDateTime convert(ObjectiveDate objective) {
        return OffsetDateTime.of(
                LocalDateTimeConvertor.getInstance().convert(objective),
                ZoneOffsetConvertor.getInstance().convert(objective)
        );
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, OffsetDateTime object) {
        if (object != null) {
            objective.setYear(object.getYear());
            objective.setMonth(object.getMonthValue());
            objective.setDay(object.getDayOfMonth());
            objective.setZone(TimeZone.getTimeZone(object.getOffset()));
            objective.setHour(object.getHour());
            objective.setMinute(object.getMinute());
            objective.setSecond(object.getSecond());
            objective.setNano(object.getNano());
        }
        return objective;
    }
}
