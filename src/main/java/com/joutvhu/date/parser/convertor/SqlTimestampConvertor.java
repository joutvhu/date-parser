package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.sql.Timestamp;

public class SqlTimestampConvertor implements Convertor<Timestamp> {
    private static SqlTimestampConvertor instance;

    public static synchronized SqlTimestampConvertor getInstance() {
        if (instance == null)
            instance = new SqlTimestampConvertor();
        return instance;
    }

    @Override
    public Timestamp convert(ObjectiveDate objective) {
        return Timestamp.valueOf(LocalDateTimeConvertor.getInstance().convert(objective));
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Timestamp object) {
        if (object != null) {
            objective.setYear(object.getYear() + 1900);
            objective.setMonth(object.getMonth() + 1);
            objective.setDay(object.getDate());
            objective.setHour(object.getHours());
            objective.setMinute(object.getMinutes());
            objective.setSecond(object.getSeconds());
            objective.setNano((int) ((object.getTime() % 1000) * 1000000));
        }
        return objective;
    }
}
