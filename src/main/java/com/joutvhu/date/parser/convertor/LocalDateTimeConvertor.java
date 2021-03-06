package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.LocalDateTime;

public class LocalDateTimeConvertor implements Convertor<LocalDateTime> {
    private static LocalDateTimeConvertor instance;

    public static synchronized LocalDateTimeConvertor getInstance() {
        if (instance == null)
            instance = new LocalDateTimeConvertor();
        return instance;
    }

    @Override
    public LocalDateTime convert(ObjectiveDate objective) {
        return LocalDateTime.of(
                LocalDateConvertor.getInstance().convert(objective),
                LocalTimeConvertor.getInstance().convert(objective)
        );
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, LocalDateTime object) {
        if (object != null) {
            objective.setYear(object.getYear());
            objective.setMonth(object.getMonthValue());
            objective.setDay(object.getDayOfMonth());
            objective.setHour(object.getHour());
            objective.setMinute(object.getMinute());
            objective.setSecond(object.getSecond());
            objective.setNano(object.getNano());
        }
        return objective;
    }
}
