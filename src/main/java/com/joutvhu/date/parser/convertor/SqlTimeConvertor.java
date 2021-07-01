package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.sql.Time;

public class SqlTimeConvertor implements Convertor<Time> {
    private static SqlTimeConvertor instance;

    public static synchronized SqlTimeConvertor getInstance() {
        if (instance == null)
            instance = new SqlTimeConvertor();
        return instance;
    }

    @Override
    public Time convert(ObjectiveDate objective) {
        return Time.valueOf(LocalTimeConvertor.getInstance().convert(objective));
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Time object) {
        if (object != null) {
            objective.setHour(object.getHours());
            objective.setMinute(object.getMinutes());
            objective.setSecond(object.getSeconds());
            objective.setNano((int) ((object.getTime() % 1000) * 1000000));
        }
        return objective;
    }
}
