package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

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
            LocalDateTime localDateTime;
            if (objective.getZone() != null) {
                localDateTime = LocalDateTime
                        .ofInstant(object, objective.getZone().toZoneId());
            } else {
                localDateTime = LocalDateTime
                        .ofInstant(object, ZoneId.systemDefault());
                objective.setZone(TimeZone.getDefault());
            }
            LocalDateTimeConvertor.getInstance().convert(objective, localDateTime);
        }
        return objective;
    }
}
