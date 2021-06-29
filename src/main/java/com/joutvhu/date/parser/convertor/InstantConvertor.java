package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Instant;

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
}
