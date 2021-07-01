package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateConvertor implements Convertor<Date> {
    private static DateConvertor instance;

    public static synchronized DateConvertor getInstance() {
        if (instance == null)
            instance = new DateConvertor();
        return instance;
    }

    @Override
    public Date convert(ObjectiveDate objective) {
        return CalendarConvertor.getInstance().convert(objective).getTime();
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Date object) {
        if (object != null) {
            objective.setYear(object.getYear());
            objective.setMonth(object.getMonth());
            objective.setDay(object.getDay());
            objective.setHour(object.getHours());
            objective.setMinute(object.getMinutes());
            objective.setSecond(object.getSeconds());
            objective.setNano((int) ((object.getTime() % 1000) * 1000000));
        }
        return objective;
    }
}
