package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.util.Date;

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

    @SuppressWarnings("java:S1874")
    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Date object) {
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
