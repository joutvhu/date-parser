package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Instant;
import java.time.temporal.ChronoField;

public class InstantConvertor implements Convertor<Instant> {
    private static InstantConvertor instance;

    public static synchronized InstantConvertor getInstance() {
        if (instance == null)
            instance = new InstantConvertor();
        return instance;
    }

    @Override
    public Instant convert(ObjectiveDate objective) {
        Instant instant = CalendarConvertor.getInstance().convert(objective).toInstant();
        int oldNano = instant.getNano();
        if (objective.getNano() != null && oldNano != objective.getNano())
            instant = instant.minusNanos(oldNano).plusNanos(objective.getNano());
        return instant;
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Instant object) {
        if (object != null) {
            objective.setYear(object.get(ChronoField.YEAR));
            objective.setMonth(object.get(ChronoField.MONTH_OF_YEAR));
            objective.setDay(object.get(ChronoField.DAY_OF_MONTH));
            objective.setHour(object.get(ChronoField.HOUR_OF_DAY));
            objective.setMinute(object.get(ChronoField.MINUTE_OF_HOUR));
            objective.setSecond(object.get(ChronoField.SECOND_OF_MINUTE));
            objective.setNano(object.get(ChronoField.NANO_OF_SECOND));
        }
        return objective;
    }
}
