package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.LocalTime;
import java.util.Objects;

public class LocalTimeConvertor implements Convertor<LocalTime> {
    private static LocalTimeConvertor instance;

    public static synchronized LocalTimeConvertor getInstance() {
        if (instance == null)
            instance = new LocalTimeConvertor();
        return instance;
    }

    @Override
    public LocalTime convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getHour());
        Objects.requireNonNull(objective.getMinute());

        LocalTime localTime = LocalTime.of(objective.getHour(), objective.getMinute());
        if (objective.getSecond() != null)
            localTime = localTime.withSecond(objective.getSecond());
        if (objective.getNano() != null)
            localTime = localTime.withNano(objective.getNano());

        return localTime;
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, LocalTime object) {
        if (object != null) {
            objective.setHour(object.getHour());
            objective.setMinute(object.getMinute());
            objective.setSecond(object.getSecond());
            objective.setNano(object.getNano());
        }
        return objective;
    }
}
